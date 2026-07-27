package com.app.serviceimplementations;

import java.math.BigDecimal;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.RazorpayOrderResponse;
import com.app.dto.VerifyPaymentRequest;
import com.app.entities.Order;
import com.app.entities.User;
import com.app.enums.OrderStatus;
import com.app.exceptions.AuthenticationException;
import com.app.exceptions.FailedException;
import com.app.exceptions.NotFoundException;
import com.app.repositories.OrderRepository;
import com.app.services.PaymentServiceContract;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentService implements PaymentServiceContract {

	private final OrderRepository orderRepository;
	private final RazorpayClient razorpayClient;

	com.razorpay.Order razorpayOrder;
	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

	@Value("${razorpay.key.id}")
	private String keyId;

	@Value("${razorpay.key.secret}")
	private String keySecret;

	public PaymentService(OrderRepository orderRepository, RazorpayClient razorpayClient) {
		super();
		this.orderRepository = orderRepository;
		this.razorpayClient = razorpayClient;
	}

	@Override
	public RazorpayOrderResponse createRazorpayOrder(int orderId, User user) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
		if (order.getUser().getUserId() != user.getUserId()) {
			throw new AuthenticationException("Unauthorized");
		}

		if (order.getStatus() != OrderStatus.PENDING) {
			throw new RuntimeException("Order already paid.");
		}

		logger.info("Creating Razorpay order for orderId {}", orderId);

		long amount = order.getTotalAmount().multiply(BigDecimal.valueOf(100)).intValue();

		JSONObject options = new JSONObject();
		options.put("amount", amount);
		options.put("currency", "INR");
		// options.put("reciept", "order_" + order.getOrderId());

		try {
			razorpayOrder = razorpayClient.orders.create(options);
			logger.info("Razorpay order created successfully: {}", razorpayOrder.get("id").toString());
		} catch (RazorpayException e) {
			logger.error("Failed to create Razorpay order", e);
			throw new FailedException("Failed to create Razorpay order");
		}
		return new RazorpayOrderResponse(razorpayOrder.get("id"), keyId, amount, "INR");
	}

	@Transactional
	@Override
	public void verifyPayment(VerifyPaymentRequest request, User user) {
		Order order = orderRepository.findById(request.getOrderId())
				.orElseThrow(() -> new NotFoundException("Order not found"));

		if (order.getUser().getUserId() != user.getUserId()) {
			throw new AuthenticationException("Unauthorized");
		}

		if (order.getStatus() != OrderStatus.PENDING) {
			throw new FailedException("Order already processed");
		}

		JSONObject attributes = new JSONObject();

		attributes.put("razorpay_order_id", request.getRazorpayOrderId());
		attributes.put("razorpay_payment_id", request.getRazorpayPaymentId());
		attributes.put("razorpay_signature", request.getRazorpaySignature());

		try {
			boolean isValid = Utils.verifyPaymentSignature(attributes, keySecret);

			if (!isValid) {
				throw new FailedException("Invalid payment signature");
			}
		} catch (RazorpayException e) {
			logger.error("Payment verification failed");
			throw new FailedException("Payment verification failed");
		}

		order.setStatus(OrderStatus.CONFIRMED);
		order.setRazorpayOrderId(request.getRazorpayOrderId());
		order.setRazorpayPaymentId(request.getRazorpayPaymentId());
		orderRepository.save(order);

	}

}
