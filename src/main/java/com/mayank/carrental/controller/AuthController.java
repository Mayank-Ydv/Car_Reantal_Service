package com.mayank.carrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mayank.carrental.dto.AuthRequest;
import com.mayank.carrental.dto.AuthResponse;
import com.mayank.carrental.entity.Role;
import com.mayank.carrental.repository.RoleRepository;
import com.mayank.carrental.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@Autowired
	private RoleRepository roleRepository;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/roles")
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		Role savedRole = roleRepository.save(role);
		return ResponseEntity.ok(savedRole);
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest req) {

		AuthResponse response = authService.register(req.getEmail(), req.getPassword(), req.getFullName(),
				req.getPhone(), req.getRoleName());

		return ResponseEntity.ok(response);

	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
		String token = authService.login(req);

		AuthResponse response = new AuthResponse();
		response.setSuccess(true);
		response.setMessage("User logged in successfully");
		response.setToken(token);

		return ResponseEntity.ok(response);
	}


}
