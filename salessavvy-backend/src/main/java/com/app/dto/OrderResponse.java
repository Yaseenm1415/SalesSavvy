package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
	private int orderId;
	private String customerName;
	private String customerEmail;
	private BigDecimal totalAmount;
	private String status;
	private LocalDateTime createdAt;
	private List<OrderItemResponse> items;

	public OrderResponse() {
		super();
	}

	public OrderResponse(int orderId, String customerName, String customerEmail, BigDecimal totalAmount, String status,
			LocalDateTime createdAt, List<OrderItemResponse> items) {
		super();
		this.orderId = orderId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.totalAmount = totalAmount;
		this.status = status;
		this.createdAt = createdAt;
		this.items = items;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<OrderItemResponse> getItems() {
		return items;
	}

	public void setItems(List<OrderItemResponse> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "OrderResponse [orderId=" + orderId + ", customerName=" + customerName + ", customerEmail="
				+ customerEmail + ", totalAmount=" + totalAmount + ", status=" + status + ", createdAt=" + createdAt
				+ ", items=" + items + "]";
	}

}
