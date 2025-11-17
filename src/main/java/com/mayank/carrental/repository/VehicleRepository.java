package com.mayank.carrental.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mayank.carrental.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Page<Vehicle> findByBranchIdAndVehicleTypeIdAndStatus(Integer branchId, Integer vehicleTypeId, String status, Pageable pageable);
    //Optional<VehicleType> findByCode(String code);
    Vehicle findVehicleById(UUID uuid);

}
