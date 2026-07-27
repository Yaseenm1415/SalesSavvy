package com.app.serviceimplementations;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.app.dto.DashboardResponse;
import com.app.enums.OrderStatus;
import com.app.repositories.OrderRepository;
import com.app.repositories.ProductRepository;
import com.app.repositories.UserRepository;
import com.app.services.DashboardServiceContract;
@Service
public class DashboardService implements DashboardServiceContract {
	ProductRepository productRepository;
	OrderRepository orderRepository;
	UserRepository userRepository;

	public DashboardService(ProductRepository productRepository, OrderRepository orderRepository,
			UserRepository userRepository) {
		super();
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
	}

	@Override
	public DashboardResponse getDashboardStats() {
		long totalUsers = userRepository.count();
		long totalProducts = productRepository.count();
		long totalOrders = orderRepository.count();
		BigDecimal totalRevenue = orderRepository.getTotalRevenue();
		long pendingOrders = orderRepository.countByStatus(OrderStatus.PENDING);

		return new DashboardResponse(totalUsers, totalProducts, totalOrders, totalRevenue, pendingOrders);
	}

}
