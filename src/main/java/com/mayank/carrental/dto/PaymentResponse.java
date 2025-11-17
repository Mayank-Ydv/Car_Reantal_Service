package com.mayank.carrental.dto;

import java.math.BigDecimal;

public record PaymentResponse(String razorpayOrderId, BigDecimal amount, String currency, String status,
		String receipt) {
}