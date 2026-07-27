package com.app.services;

import com.app.dto.RegisterRequest;
import com.app.dto.UserResponse;

public interface UserServiceContract {
	UserResponse userRegister(RegisterRequest request);
}
