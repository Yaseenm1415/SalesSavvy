package com.app.dto;

import java.time.LocalDateTime;

import com.app.entities.User;
import com.app.enums.UserRole;

public class UserResponse {
	private int userId;
	private String username;
	private String email;
	private UserRole role;
	private LocalDateTime createdAt;

	public UserResponse() {
	}

	public UserResponse(int userId, String username, String email, UserRole role, LocalDateTime createdAt) {
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.role = role;
		this.createdAt = createdAt;
	}

	public static UserResponse from(User user) {
		return new UserResponse(
				user.getUserId(),
				user.getUsername(),
				user.getEmail(),
				user.getRole(),
				user.getCreatedAt());
	}

	public int getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public UserRole getRole() {
		return role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
