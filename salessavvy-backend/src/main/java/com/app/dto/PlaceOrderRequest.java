package com.app.dto;

import java.util.List;

public class PlaceOrderRequest {
	private List<Integer> cartItemsIds;

	public PlaceOrderRequest() {
		super();

	}

	public PlaceOrderRequest(List<Integer> cartItemsIds) {
		super();
		this.cartItemsIds = cartItemsIds;
	}

	public List<Integer> getCartItemsIds() {
		return cartItemsIds;
	}

	public void setCartItemsIds(List<Integer> cartItemsIds) {
		this.cartItemsIds = cartItemsIds;
	}

	@Override
	public String toString() {
		return "PlaceOrderRequest [cartItemsIds=" + cartItemsIds + "]";
	}

}
