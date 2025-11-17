package com.mayank.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
   @GetMapping("/user/home")
   public ResponseEntity<String> userHome() {
//       return ResponseEntity.ok();
       try {
			return ResponseEntity.ok("Welcome USER! You have successfully accessed the user dashboard.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong: " + e.getMessage());
		}
   }
   
   @GetMapping("/admin/home")
   public ResponseEntity<String> AdminHome() {
//       return ResponseEntity.ok();
       try {
			return ResponseEntity.ok("Welcome Admin! You have successfully accessed the user dashboard.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong: " + e.getMessage());
		}
   }
   
   
}

