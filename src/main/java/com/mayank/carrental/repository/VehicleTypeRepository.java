package com.mayank.carrental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mayank.carrental.entity.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {
	Optional<VehicleType> findByCode(String code);
}
