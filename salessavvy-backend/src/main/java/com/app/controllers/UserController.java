package com.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.RegisterRequest;
import com.app.dto.UserResponse;
import com.app.services.UserServiceContract;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
	private UserServiceContract userService;

	public UserController(UserServiceContract userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest request) {

		UserResponse registeredUser = userService.userRegister(request);
		return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", registeredUser));

	}
}
