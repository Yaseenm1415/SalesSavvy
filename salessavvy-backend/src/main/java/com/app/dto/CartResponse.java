package com.app.dto;

import java.math.BigDecimal;

public class CartResponse {
	private int cartId;
	private int productId;
	private String productName;
	private BigDecimal price;
	private int quantity;
	private BigDecimal subtotal;
	private String imageUrl;

	public CartResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartResponse(int cartId, int productId, String productName, BigDecimal price, int quantity,
			BigDecimal subtotal, String imageUrl) {
		super();
		this.cartId = cartId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.imageUrl = imageUrl;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

}
