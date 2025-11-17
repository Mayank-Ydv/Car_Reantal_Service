package com.mayank.carrental.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mayank.carrental.dto.AuthRequest;
import com.mayank.carrental.dto.AuthResponse;
import com.mayank.carrental.entity.Role;
import com.mayank.carrental.entity.User;
import com.mayank.carrental.repository.RoleRepository;
import com.mayank.carrental.repository.UserRepository;
import com.mayank.carrental.util.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Autowired
	public AuthService(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Transactional
	public AuthResponse register(String email, String password, String fullName, String phone, String roleName) {
		Optional<User> existing = userRepository.findByEmail(email);
		if (existing.isPresent()) {
			throw new RuntimeException("Email already registered");
		}

		Role role = roleRepository.findByName(roleName);

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail(email);
		user.setPasswordHash(passwordEncoder.encode(password));
		user.setFullName(fullName);
		user.setPhone(phone);
		user.setRole(role);

		userRepository.save(user);

		String token = jwtUtil.generateToken(user.getId().toString(), user.getEmail(), role.getName());
		String message = "User created Successfully";

		return new AuthResponse(token, user.getId(), user.getEmail(), user.getFullName(), role.getName(), true, // success
				"User registered successfully" // message
		);

	}

	public String login(AuthRequest req) {
		User user = userRepository.findByEmail(req.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid credentials"));

		if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
			throw new RuntimeException("Invalid credentials");
		}

		return jwtUtil.generateToken(user.getId().toString(), user.getEmail(),
				user.getRole() != null ? user.getRole().getName() : "ROLE_USER");

	}
}