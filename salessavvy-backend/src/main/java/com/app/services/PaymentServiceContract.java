package com.app.services;

import com.app.dto.RazorpayOrderResponse;
import com.app.dto.VerifyPaymentRequest;
import com.app.entities.User;

public interface PaymentServiceContract {
	RazorpayOrderResponse createRazorpayOrder(int orderId, User user);
	void verifyPayment(VerifyPaymentRequest request, User user);
}
