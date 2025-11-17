package com.mayank.carrental.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mayank.carrental.entity.Branch;
import com.mayank.carrental.entity.Vehicle;
import com.mayank.carrental.entity.VehicleType;
import com.mayank.carrental.repository.BranchRepository;
import com.mayank.carrental.repository.VehicleRepository;
import com.mayank.carrental.repository.VehicleTypeRepository;
import com.mayank.carrental.service.VehicleService;

@RestController
@RequestMapping("/api/admin/vehicles")
public class VehicleController {
	private final VehicleService vehicleService;

	@Autowired
	private VehicleTypeRepository vehicleTypeRepository;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private VehicleRepository vehicleRepository;

	public VehicleController(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Vehicle> get(@PathVariable UUID id) {
		return ResponseEntity.ok(vehicleService.getById(id));
	}

	@PostMapping
	public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {

		Branch branch = branchRepository.findById(vehicle.getBranch().getId())
				.orElseThrow(() -> new RuntimeException("Branch not found"));

		String vehicleTypeCode = vehicle.getVehicleType().getCode();
		VehicleType vehicleType = vehicleTypeRepository.findByCode(vehicleTypeCode)
				.orElseThrow(() -> new RuntimeException("Vehicle type not found"));

		vehicle.setId(UUID.randomUUID());
		vehicle.setBranch(branch);
		vehicle.setVehicleType(vehicleType);

		Vehicle savedVehicle = vehicleRepository.save(vehicle);
		return ResponseEntity.ok(savedVehicle);

	}

}
