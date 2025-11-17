package com.mayank.carrental.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mayank.carrental.entity.Booking;
import com.mayank.carrental.entity.Vehicle;
import com.mayank.carrental.repository.BookingRepository;
import com.mayank.carrental.repository.VehicleRepository;

@Service
public class VehicleService {
	@Autowired
	public BookingRepository bookingRepository;
	private final VehicleRepository vehicleRepository;

	public VehicleService(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	public Page<Vehicle> search(Integer branchId, Integer typeId, Pageable pageable) {
		return vehicleRepository.findByBranchIdAndVehicleTypeIdAndStatus(branchId, typeId, "AVAILABLE", pageable);
	}

	public Vehicle getById(UUID id) {
		return vehicleRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle not found"));
	}

	public Booking getVechileDropOff(UUID userId) {
		// TODO Auto-generated method stub
		Booking userData = bookingRepository.findBookingByUserId(userId);
		// System.out.println(userData);

		if (userData == null) {
			throw new RuntimeException("No booking found for this user.");
		}

//		// Check if booking end time has passed
//		if (userData.getEndTs().isBefore(LocalDateTime.now())) {
//			throw new RuntimeException("You need to pay a fine as you have exceeded the drop-off time.");
//		}
		 Vehicle v = vehicleRepository.findVehicleById(userData.getVehicle().getId());
		 System.out.println(v);
		 v.setStatus("AVAILABLE");
		 System.out.println("updated Successfully");
		 bookingRepository.deleteById(userData.getId());
		 return userData;
	}
}
