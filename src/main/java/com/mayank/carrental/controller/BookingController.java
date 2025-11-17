package com.mayank.carrental.controller;

import static java.util.UUID.fromString;

import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mayank.carrental.dto.CreateBookingRequest;
import com.mayank.carrental.entity.Booking;
import com.mayank.carrental.entity.Vehicle;
import com.mayank.carrental.repository.BookingRepository;
import com.mayank.carrental.repository.VehicleRepository;
import com.mayank.carrental.service.BookingService;
import com.mayank.carrental.service.VehicleService;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/user/bookings")
public class BookingController {
	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@Autowired
	private VehicleService vehicleService;
	@Autowired
	public BookingRepository bookingRepository;
	public VehicleRepository vehicleRepository;
	@Value("${razorpay.key_secret}")
	private String keySecret;

	@PostMapping
	public ResponseEntity<Booking> create(@RequestBody CreateBookingRequest req, Authentication auth) {
		String userIdStr = (String) auth.getPrincipal();
		UUID userId = fromString(userIdStr);
		Booking b = bookingService.createBooking(userId, req);
		return ResponseEntity.status(201).body(b);
	}
	
	@PostMapping("/update-status")
	public ResponseEntity<String> updateBookingStatus(@RequestBody Map<String, String> request) {
	    String bookingId = request.get("bookingId");
	    String status = request.get("status");

	    Booking booking = bookingRepository.findById(UUID.fromString(bookingId))
	        .orElseThrow(() -> new RuntimeException("Booking not found"));

	    booking.setStatus(status);

	    if ("COMPLETED".equalsIgnoreCase(status)) {
	        Vehicle vehicle = booking.getVehicle();
	        vehicle.setStatus("RENTED");
	        vehicleRepository.save(vehicle);
	    }

	    bookingRepository.save(booking);
	    return ResponseEntity.ok("Booking status updated successfully");
	}
	
	@PostMapping("/verify-payment")
	@Transactional
	public ResponseEntity<String> verifyPayment(@RequestBody Map<String, String> request) {
	    String orderId = request.get("razorpay_order_id");
	    String paymentId = request.get("razorpay_payment_id");
	    String signature = request.get("razorpay_signature");
	    String bookingId = request.get("bookingId");

	    boolean isValid = bookingService.verifySignature(orderId, paymentId, signature, keySecret);
	    if (!isValid) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
	    }

	    Booking booking = bookingRepository.findById(UUID.fromString(bookingId))
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    booking.setStatus("COMPLETED");
	    Vehicle v = booking.getVehicle();
	    v.setStatus("RENTED");
	    vehicleRepository.save(v);
	    bookingRepository.save(booking);

	    return ResponseEntity.ok("Payment verified and booking updated");
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<Page<Vehicle>> search(@RequestParam Integer branchId, @RequestParam Integer typeId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Page<Vehicle> p = vehicleService.search(branchId, typeId, PageRequest.of(page, size));
		return ResponseEntity.ok(p);
	}

	@DeleteMapping("/drop")
	public ResponseEntity<Booking> droppoff(@RequestParam UUID userId) {
		Booking savedData = vehicleService.getVechileDropOff(userId);
		return ResponseEntity.ok(savedData);
	}
}
