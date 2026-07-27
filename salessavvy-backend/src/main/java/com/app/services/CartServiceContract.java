package com.app.services;

import java.util.List;

import com.app.dto.CartRequest;
import com.app.dto.CartResponse;
import com.app.entities.User;

public interface CartServiceContract {
	CartResponse addToCart(User user, CartRequest request);
	List<CartResponse> getCartItems(User user);
	CartResponse updateQuantity(int cartId, int quantity, User user);
	void removeFromCart(int cartId, User user);
}
