package com.app.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
	private int productId;
	private String productName;
	private int quantity;
	private BigDecimal priceAtPurchase;
	private BigDecimal subtotal;
	private String imageUrl;

	public OrderItemResponse() {
		super();
	}

	public OrderItemResponse(int productId, String productName, int quantity, BigDecimal priceAtPurchase,
			BigDecimal subtotal, String imageUrl) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.priceAtPurchase = priceAtPurchase;
		this.subtotal = subtotal;
		this.imageUrl = imageUrl;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPriceAtPurchase() {
		return priceAtPurchase;
	}

	public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
		this.priceAtPurchase = priceAtPurchase;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "OrderItemResponse [productId=" + productId + ", productName=" + productName + ", quantity=" + quantity
				+ ", priceAtPurchase=" + priceAtPurchase + ", subtotal=" + subtotal + ", imageUrl=" + imageUrl + "]";
	}

}
