package com.app.services;

import com.app.entities.User;
import com.app.enums.UserRole;

public interface AuthServiceContract {
	User authenticate(String username, String password, UserRole expectedRole);
	String generateAccessToken(User user);
	String generateRefreshToken(User user);
	void saveRefreshToken(User user, String refreshToken);
	boolean validateAccessToken(String accessToken);
	boolean validateRefreshToken(String refreshToken);
	String extractUsername(String token);
	void logout(String token);
}
