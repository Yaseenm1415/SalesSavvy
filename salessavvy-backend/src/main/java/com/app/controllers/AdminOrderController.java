package com.app.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.UpdateOrderStatusRequest;
import com.app.entities.User;
import com.app.enums.UserRole;
import com.app.services.OrderServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/orders")
public class AdminOrderController {
	OrderServiceContract orderService;

	public AdminOrderController(OrderServiceContract orderService) {
		super();
		this.orderService = orderService;
	}

	@GetMapping
	public ResponseEntity<?> getAllOrders(HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null || user.getRole() != UserRole.ADMIN) {
			throw new RuntimeException("Unauthenticated");
		}
		return ResponseEntity.ok(orderService.getAllOrders());

	}

	@PutMapping("/{orderId}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId, @RequestBody UpdateOrderStatusRequest request,
			HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null || user.getRole() != UserRole.ADMIN) {
			throw new RuntimeException("Unauthenticated");
		}
		return ResponseEntity.ok(orderService.updateOrderStatus(orderId, request.getStatus()));

	}

	@GetMapping("/{orderId}")
	public ResponseEntity<?> getOrderByIdForAdmin(@PathVariable int orderId, HttpServletRequest httpRequest) {

		User user = (User) httpRequest.getAttribute("authenticatedUser");
		if (user == null || user.getRole() != UserRole.ADMIN) {
			throw new RuntimeException("Unauthenticated");
		}
		return ResponseEntity.ok(orderService.getOrderByIdForAdmin(orderId));

	}
}
