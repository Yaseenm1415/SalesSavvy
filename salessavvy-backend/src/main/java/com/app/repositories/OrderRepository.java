package com.app.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.Order;
import com.app.entities.User;
import com.app.enums.OrderStatus;
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findByUser(User user);
	Optional<Order> findByOrderIdAndUser(int orderId, User user);
	long countByStatus(OrderStatus status);
	@Query("""
			SELECT COALESCE(SUM(o.totalAmount),0)
			FROM Order o
			WHERE o.status = 'DELIVERED'
			""")
	BigDecimal getTotalRevenue();
}
