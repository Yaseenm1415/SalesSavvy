package com.app.services;

import java.util.List;

import com.app.dto.OrderResponse;
import com.app.dto.PlaceOrderRequest;
import com.app.entities.User;
import com.app.enums.OrderStatus;

public interface OrderServiceContract {
	OrderResponse placeOrder(User user, PlaceOrderRequest request);
	List<OrderResponse> getOrders(User user);
	OrderResponse getOrderById(int orderId, User user);
	List<OrderResponse> getAllOrders();
	OrderResponse updateOrderStatus(int orderId, OrderStatus status);
	OrderResponse getOrderByIdForAdmin(int orderId);
	void cancelOrder(int orderId, int userId);

}
