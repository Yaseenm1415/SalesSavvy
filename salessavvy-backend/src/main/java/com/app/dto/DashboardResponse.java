package com.app.dto;

import java.math.BigDecimal;

public class DashboardResponse {
	private long totalUsers;
	private long totalProducts;
	private long totalOrders;
	private BigDecimal totalRevenue;
	private long pendingOrders;

	public DashboardResponse() {
		super();
	}

	public DashboardResponse(long totalUsers, long totalProducts, long totalOrders, BigDecimal totalRevenue,
			long pendingOrders) {
		super();
		this.totalUsers = totalUsers;
		this.totalProducts = totalProducts;
		this.totalOrders = totalOrders;
		this.totalRevenue = totalRevenue;
		this.pendingOrders = pendingOrders;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}

	public long getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(long totalOrders) {
		this.totalOrders = totalOrders;
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public long getPendingOrders() {
		return pendingOrders;
	}

	public void setPendingOrders(long pendingOrders) {
		this.pendingOrders = pendingOrders;
	}

	@Override
	public String toString() {
		return "DashboardResponse [totalUsers=" + totalUsers + ", totalProducts=" + totalProducts + ", totalOrders="
				+ totalOrders + ", totalRevenue=" + totalRevenue + ", pendingOrders=" + pendingOrders + "]";
	}

}
