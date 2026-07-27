package com.app.serviceimplementations;

import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entities.JWTToken;
import com.app.entities.User;
import com.app.enums.TokenType;
import com.app.enums.UserRole;
import com.app.exceptions.AccesDeniedException;
import com.app.exceptions.AuthenticationException;
import com.app.repositories.JWTTokenRepository;
import com.app.repositories.UserRepository;
import com.app.services.AuthServiceContract;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService implements AuthServiceContract {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	private final SecretKey SIGNING_KEY;
	private final UserRepository userRepository;
	private final JWTTokenRepository jwtTokenRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, JWTTokenRepository jwtTokenRepository,
			PasswordEncoder passwordEncoder, @Value("${jwt.secret}") String jwtSecret) {
		this.userRepository = userRepository;
		this.jwtTokenRepository = jwtTokenRepository;
		this.passwordEncoder = passwordEncoder;

		if (jwtSecret == null || jwtSecret.isBlank()) {
			throw new IllegalArgumentException(
					"jwt.secret must be set in application-local.properties or JWT_SECRET env variable.");
		}
		if (jwtSecret.getBytes(StandardCharsets.UTF_8).length < 64) {
			throw new IllegalArgumentException("jwt.secret must be at least 64 bytes long for HS512.");
		}
		this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public User authenticate(String username, String password, UserRole expectedRole) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new AuthenticationException("Invalid username or password"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new AuthenticationException("Invalid username or password");
		}
		if (user.getRole() != expectedRole) {
			throw new AccesDeniedException("Access denied: invalid role for this login path");
		}
		return user;
	}



	@Override
	public String generateAccessToken(User user) {

		return Jwts.builder().subject(user.getUsername()).claim("role", user.getRole().name()).claim("tokenType", TokenType.ACCESS.name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes
				.signWith(SIGNING_KEY).compact();
	}
	
	@Override
	public String generateRefreshToken(User user) {
		return Jwts.builder().subject(user.getUsername()).claim("tokenType", TokenType.REFRESH.name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000)) // 7 days
				.signWith(SIGNING_KEY).compact();
	}

	@Override
	public void saveRefreshToken(User user, String RefreshToken) {

	    jwtTokenRepository.findByUser_UserId(user.getUserId())
	            .ifPresent(token -> jwtTokenRepository.delete(token));
		JWTToken jwtToken = new JWTToken(user, RefreshToken, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
		jwtTokenRepository.save(jwtToken);
	}

	@Override
	public boolean validateAccessToken(String accessToken) {
		try {
			Claims claims = Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(accessToken).getPayload();

				return TokenType.ACCESS.name().equals(claims.get("tokenType", String.class));
		} catch (Exception e) {
			logger.debug("Access token validation failed: {}", e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean validateRefreshToken(String refreshToken) {
		try {
			Claims claims = Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(refreshToken).getPayload();
			
			if(!TokenType.REFRESH.name().equals(claims.get("tokenType", String.class))) {
				return false;
			}
			Optional<JWTToken> jwtToken = jwtTokenRepository.findByToken(refreshToken);
			

			return jwtToken.isPresent() && jwtToken.get().getExpiresAt().isAfter(LocalDateTime.now());
		} catch (Exception e) {
			logger.debug("Refresh token validation failed: {}", e.getMessage());
			return false;
		}
	}

	@Override
	public String extractUsername(String token) {

		return Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(token).getPayload().getSubject();

	}

	@Override
	public void logout(String refreshToken) {
		JWTToken jwtToken = jwtTokenRepository.findByToken(refreshToken)
				.orElseThrow(() -> new AuthenticationException("Already logged out"));

		jwtTokenRepository.delete(jwtToken);
	}



}
