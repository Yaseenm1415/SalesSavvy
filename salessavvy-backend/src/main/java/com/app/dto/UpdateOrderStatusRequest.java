package com.app.dto;

import com.app.enums.OrderStatus;

public class UpdateOrderStatusRequest {
	private OrderStatus status;

	public UpdateOrderStatusRequest() {
		super();
	}

	public UpdateOrderStatusRequest(OrderStatus status) {
		super();
		this.status = status;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UpdateOrderStatusRequest [status=" + status + "]";
	}

}
