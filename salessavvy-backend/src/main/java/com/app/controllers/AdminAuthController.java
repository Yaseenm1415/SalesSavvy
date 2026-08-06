package com.app.controllers;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.User;
import com.app.enums.UserRole;
import com.app.services.AuthServiceContract;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminAuthController {

	private final AuthServiceContract authService;

	public AdminAuthController(AuthServiceContract authService) {
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> request) {
		
			String username = request.get("username");
			String password = request.get("password");

			User user = authService.authenticate(username, password, UserRole.ADMIN);
			String accessToken = authService.generateAccessToken(user);
			String refreshToken = authService.generateRefreshToken(user);
			
			authService.saveRefreshToken(user, refreshToken);

			ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.maxAge(3600)
					.sameSite("Lax")
					.build();
			
			ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
					.httpOnly(true)
					.secure(false)
					.path("/")
					.maxAge(3600)
					.sameSite("Lax")
					.build();

			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
					.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
					.body(Map.of("message", "Login successful", "role", user.getRole().name(), "username",
							user.getUsername()));
		
	}
}
