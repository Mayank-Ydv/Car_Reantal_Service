package com.mayank.carrental.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(BigDecimal amount,  String customerName,
		 String customerEmail,
		 String customerContact,
		 UUID bookingId// optional if you want to associate booking
		) {
}