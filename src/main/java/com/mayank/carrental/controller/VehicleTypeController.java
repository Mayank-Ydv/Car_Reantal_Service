package com.mayank.carrental.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mayank.carrental.entity.VehicleType;
import com.mayank.carrental.repository.VehicleTypeRepository;

@RestController
@RequestMapping("/api/admin/vehicle-types")
public class VehicleTypeController {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @PostMapping
    public ResponseEntity<VehicleType> addVehicleType(@RequestBody VehicleType vehicleType) {
        VehicleType savedType = vehicleTypeRepository.save(vehicleType);
        return ResponseEntity.ok(savedType);
    }

    @GetMapping
    public ResponseEntity<?> getAllVehicleTypes() {
        return ResponseEntity.ok(vehicleTypeRepository.findAll());
    }
}
