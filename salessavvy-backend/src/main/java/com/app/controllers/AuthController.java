package com.app.controllers;

import java.util.Arrays;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.LoginRequest;
import com.app.entities.User;
import com.app.enums.UserRole;
import com.app.exceptions.AuthenticationException;
import com.app.repositories.UserRepository;
import com.app.services.AuthServiceContract;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
	private AuthServiceContract authService;
	private UserRepository userRepository;

	public AuthController(AuthServiceContract authService, UserRepository userRepository) {
		super();
		this.authService = authService;
		this.userRepository = userRepository;
	}

	@PostMapping("/api/user/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {

		String username = request.getUsername();
		String password = request.getPassword();

		User user = authService.authenticate(username, password, UserRole.CUSTOMER);
		String accessToken = authService.generateAccessToken(user);
		String refreshToken = authService.generateRefreshToken(user);
		authService.saveRefreshToken(user, refreshToken);

		ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken).httpOnly(true).secure(true)
				.path("/").maxAge(15 * 60).sameSite("None").build();

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true).secure(true)
				.path("/").maxAge(7 * 24 * 60 * 60).sameSite("None").build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
				.header(HttpHeaders.SET_COOKIE, refreshCookie.toString()).body(Map.of("message", "Login successful",
						"role", user.getRole().name(), "username", user.getUsername()));

	}
	
	@GetMapping("/me")
	public ResponseEntity<?> currentUser(Authentication authentication) {

	    User user = (User) authentication.getPrincipal();

	    return ResponseEntity.ok(Map.of(
	            "username", user.getUsername(),
	            "role", user.getRole().name()
	    ));
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {

		String refreshToken = getCookie(request, "refreshToken");

		if (refreshToken != null) {
			authService.logout(refreshToken);
		}

		ResponseCookie accessCookie = ResponseCookie.from("accessToken", "").httpOnly(true).secure(true).path("/")
				.maxAge(0).sameSite("None").build();

		ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", "").httpOnly(true).secure(true).path("/")
				.maxAge(0).sameSite("None").build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
				.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(Map.of("message", "Logged out successfully"));
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(HttpServletRequest request) {

		String refreshToken = getCookie(request, "refreshToken");

	    System.out.println("Refresh Token: " + refreshToken);


		if (refreshToken == null) {
			throw new AuthenticationException("Refresh token is missing");
		}

		if (!authService.validateRefreshToken(refreshToken)) {
			throw new AuthenticationException("Invalid refresh token");

		}
	    System.out.println("Valid: " + authService.validateRefreshToken(refreshToken));


		String username = authService.extractUsername(refreshToken);

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new AuthenticationException("User not found"));

		String accessToken = authService.generateAccessToken(user);

		ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken).httpOnly(true).secure(true)
				.path("/").maxAge(15 * 60).sameSite("None").build();
		
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, accessCookie.toString())
				.body(Map.of("message", "Access token refreshed"));
	}
	
	private String getCookie(HttpServletRequest request, String cookieName) {
	    Cookie[] cookies = request.getCookies();

	    if (cookies == null) {
	        return null;
	    }

	    return Arrays.stream(cookies)
	            .filter(cookie -> cookieName.equals(cookie.getName()))
	            .map(Cookie::getValue)
	            .findFirst()
	            .orElse(null);
	}
}
