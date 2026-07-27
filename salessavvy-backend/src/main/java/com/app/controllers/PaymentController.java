package com.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.VerifyPaymentRequest;
import com.app.entities.User;
import com.app.exceptions.AuthenticationException;
import com.app.services.PaymentServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	private final PaymentServiceContract paymentService;

	public PaymentController(PaymentServiceContract paymentService) {
		super();
		this.paymentService = paymentService;
	}
	
	@PostMapping("/razorpay-order/{orderId}")
	public ResponseEntity<?> createRazorpayOrder(@PathVariable int orderId, HttpServletRequest request) {
		User user =(User) request.getAttribute("authenticatedUser");
		
		System.out.println("Authenticated User = " + user);
		
		if(user == null) {
			throw new AuthenticationException("Unauthenticated");
		}
		return ResponseEntity.ok(paymentService.createRazorpayOrder(orderId, user));
		
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyPayment(@RequestBody VerifyPaymentRequest request, HttpServletRequest httpRequest) {
		User user = (User) httpRequest.getAttribute("authenticatedUser");
		
		if(user == null) {
			throw new AuthenticationException("Unauthorized");
		}
		paymentService.verifyPayment(request, user);
		return ResponseEntity.ok(Map.of("message", "Payment verified successfully", "orderId", request.getOrderId()));
	}

}
