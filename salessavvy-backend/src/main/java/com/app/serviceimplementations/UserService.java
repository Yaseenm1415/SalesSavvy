package com.app.serviceimplementations;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.RegisterRequest;
import com.app.dto.UserResponse;
import com.app.entities.User;
import com.app.enums.UserRole;
import com.app.exceptions.AlreadyExistsException;
import com.app.repositories.UserRepository;
import com.app.services.UserServiceContract;

@Service
public class UserService implements UserServiceContract {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserResponse userRegister(RegisterRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AlreadyExistsException("Username is already taken");
		}

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new AlreadyExistsException("Email is already registered");
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(UserRole.CUSTOMER);

		return UserResponse.from(userRepository.save(user));
	}

}
