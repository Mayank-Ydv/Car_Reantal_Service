package com.mayank.carrental.dto;

import java.util.UUID;

public class AuthResponse {
	private String token;
	private UUID userId;
	private String email;
	private String fullName;
	private String role;
	private boolean success;
	private String message;

	public AuthResponse() {
	}

//	public AuthResponse(String token, UUID userId, String email, String fullName, String role , String message ) {
//		this.token = token;
//		this.userId = userId;
//		this.email = email;
//		this.fullName = fullName;
//		this.role = role;
//
//	}




public AuthResponse(String token, UUID userId, String email, String fullName, String role, boolean success, String message) {
    this.token = token;
    this.userId = userId;
    this.email = email;
    this.fullName = fullName;
    this.role = role;
    this.success = success;
    this.message = message;
}


	// No-arg constructor (automatically provided if you don't define any
	// constructor)

	 public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	// Getters and setters
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}