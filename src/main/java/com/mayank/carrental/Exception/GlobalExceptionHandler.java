package com.mayank.carrental.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mayank.carrental.dto.AuthResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AuthResponse> handleRuntimeException(RuntimeException ex) {
        AuthResponse response = new AuthResponse();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());


return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response); // ✅ this is correct

    }

    // You can add more handlers for other exception types
}
