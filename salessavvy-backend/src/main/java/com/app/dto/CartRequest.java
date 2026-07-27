package com.app.dto;

public class CartRequest {
	private int productId;
	private int quantity;

	public CartRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartRequest(int productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
