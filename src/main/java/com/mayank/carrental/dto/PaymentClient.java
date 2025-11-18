package com.mayank.carrental.dto;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paymentMicroservice", url = "http://localhost:8086", path ="/payments" )
public interface PaymentClient {

	@PostMapping("/create-payment-link")
	PaymentResponse createPaymentLink(@RequestBody PaymentRequest request);
	
	 @PostMapping("/razorpay-webhook")
	    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload,
	                                                @RequestHeader("X-Razorpay-Signature") String signature);
}