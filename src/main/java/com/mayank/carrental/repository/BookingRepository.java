package com.mayank.carrental.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayank.carrental.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    // Additional query methods can be added
	 public Booking findBookingByUserId(UUID userId);

	//public Vehicle findBookingByUserId(UUID userId);
}
