package com.app.controllers;


import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PlaceOrderRequest;
import com.app.entities.User;
import com.app.services.OrderServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class OrderController {
	private OrderServiceContract orderService;

	public OrderController(OrderServiceContract orderService) {
		super();
		this.orderService = orderService;
	}

	@PostMapping("/orders")
	public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest request, HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			throw new RuntimeException("Unauthenticated");
		}

		return ResponseEntity.ok(orderService.placeOrder(user, request));

	}

	@GetMapping("/orders")
	public ResponseEntity<?> getOrders(HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			throw new RuntimeException("Unauthenticated");
		}

		return ResponseEntity.ok(orderService.getOrders(user));

	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<?> getOrderById(@PathVariable int orderId, HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null) {
			throw new RuntimeException("Unauthenticated");
		}

		return ResponseEntity.ok(orderService.getOrderById(orderId, user));

	}
	
	@PutMapping("/orders/{orderId}/cancel")
	public ResponseEntity<?> cancelOrder(
	        @PathVariable int orderId,
	        HttpServletRequest request) {

	    User user = (User) request.getAttribute("authenticatedUser");

	    if (user == null) {
	        throw new RuntimeException("Unauthenticated");
	    }
	    
	    orderService.cancelOrder(orderId, user.getUserId());

	    return ResponseEntity.ok(Map.of("message", "Order cancelled successfully"));
	}
}
