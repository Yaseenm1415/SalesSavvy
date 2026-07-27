package com.app.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_tokens")
public class JWTToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int tokenId;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	User user;
	@Column(nullable = false)
	String token;
	@Column(nullable = false)
	LocalDateTime createdAt;
	@Column(nullable = false)
	LocalDateTime expiresAt;

	public JWTToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JWTToken(User user, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		super();
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}

	public JWTToken(int tokenId, User user, String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
		super();
		this.tokenId = tokenId;
		this.user = user;
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User userId) {
		this.user = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public String toString() {
		return "JWTToken [tokenId=" + tokenId + ", user=" + user + ", token=" + token + ", createdAt=" + createdAt
				+ ", expiresAt=" + expiresAt + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, expiresAt, token, tokenId, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JWTToken other = (JWTToken) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(expiresAt, other.expiresAt)
				&& Objects.equals(token, other.token) && tokenId == other.tokenId
				&& Objects.equals(user, other.user);
	}

}
