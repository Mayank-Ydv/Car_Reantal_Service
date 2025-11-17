package com.mayank.carrental.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayank.carrental.dto.CreateBookingRequest;
import com.mayank.carrental.dto.PaymentClient;
import com.mayank.carrental.dto.PaymentRequest;
import com.mayank.carrental.dto.PaymentResponse;
import com.mayank.carrental.entity.Booking;
import com.mayank.carrental.entity.Vehicle;
import com.mayank.carrental.repository.BookingRepository;
import com.mayank.carrental.repository.VehicleRepository;




@Service
public class BookingService {

	private final BookingRepository bookingRepository;
	private final VehicleRepository vehicleRepository;
	private final PaymentClient paymentClient;

	public BookingService(BookingRepository bookingRepository, VehicleRepository vehicleRepository,
			PaymentClient paymentClient) {
		this.bookingRepository = bookingRepository;
		this.vehicleRepository = vehicleRepository;
		this.paymentClient = paymentClient;
	}

	 public boolean verifySignature(String orderId, String paymentId, String razorpaySignature, String keySecret) {
	        try {
	            String data = orderId + "|" + paymentId;
	            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	            SecretKeySpec secretKey = new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");
	            sha256_HMAC.init(secretKey);
	            String generatedSignature = Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(data.getBytes()));
	            return generatedSignature.equals(razorpaySignature);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	@Transactional
	public Booking createBooking(UUID userId, CreateBookingRequest req) {
		Vehicle v = vehicleRepository.findById(req.getVehicleId())
				.orElseThrow(() -> new RuntimeException("Vehicle not found"));

		if (!"AVAILABLE".equalsIgnoreCase(v.getStatus())) {
			throw new RuntimeException("Vehicle not available");
		}

		long days = Math.max(1, Duration.between(req.getStartTs(), req.getEndTs()).toDays());
		BigDecimal total = v.getDailyBasePrice() != null ? v.getDailyBasePrice().multiply(BigDecimal.valueOf(days))
				: BigDecimal.ZERO;



		Booking b = new Booking();
		b.setId(UUID.randomUUID());
		b.setUserId(userId);
		b.setVehicle(v);
		b.setStartTs(req.getStartTs());
		b.setEndTs(req.getEndTs());
		b.setBranchPickupId(req.getPickupBranch());
		b.setBranchReturnId(req.getReturnBranch());
		b.setTotalPrice(total);
		b.setStatus("PENDING");
		bookingRepository.save(b);
		System.out.println("Hi initiating payment request");

		System.out.println("Creating Razorpay Order...");
		
		// Call Payment Microservice via Feign
		PaymentRequest paymentReq = new PaymentRequest(total ,   "Mayank Yadav",
				  "my240669@gmail.com",
				 "9918050815",b.getId()); // bookingId can be set after booking creation
		PaymentResponse paymentResp = paymentClient.createPaymentLink(paymentReq);
		//Create payment link
		if (paymentResp == null || !"created".equalsIgnoreCase(paymentResp.status())) {
			throw new RuntimeException(
					"Payment failed: " + (paymentResp != null ? paymentResp.status() : "Unknown error"));
		}
		v.setStatus("RENTED");
		b.setStatus("COMPLETED");
		vehicleRepository.save(v);

		return b;
	}
}