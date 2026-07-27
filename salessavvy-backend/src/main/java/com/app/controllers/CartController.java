package com.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.CartRequest;
import com.app.dto.UpdateCartRequest;
import com.app.entities.User;
import com.app.services.CartServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private CartServiceContract cartService;

	public CartController(CartServiceContract cartService) {
		super();
		this.cartService = cartService;
	}

	@PostMapping
	public ResponseEntity<?> addToCart(@RequestBody CartRequest request, HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
		}

		return ResponseEntity.ok(cartService.addToCart(user, request));

	}

	@GetMapping
	public ResponseEntity<?> getCartItems(HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
		}
		return ResponseEntity.ok(cartService.getCartItems(user));

	}

	@PutMapping("/{cartId}")
	public ResponseEntity<?> updateQuantity(@PathVariable int cartId, @RequestBody UpdateCartRequest request,
			HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
		}
		return ResponseEntity.ok(cartService.updateQuantity(cartId, request.getQuantity(), user));

	}

	@DeleteMapping("/{cartId}")
	public ResponseEntity<?> removefromCart(@PathVariable int cartId, HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
		}
		cartService.removeFromCart(cartId, user);
		return ResponseEntity.ok(Map.of("message", "Item removed from cart"));

	}
}
