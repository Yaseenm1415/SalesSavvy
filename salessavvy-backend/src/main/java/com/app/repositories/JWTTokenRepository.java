package com.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.JWTToken;
@Repository
public interface JWTTokenRepository extends JpaRepository<JWTToken, Integer>{
	Optional<JWTToken> findByUser_UserId(int userId);
	Optional<JWTToken> findByToken(String token);
}
