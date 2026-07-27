package com.app.dto;

public class VerifyPaymentRequest {

	private int orderId;
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String razorpaySignature;

	public VerifyPaymentRequest() {
		super();
	}

	public VerifyPaymentRequest(int orderId, String razorpayOrderId, String razorpayPaymentId,
			String razorpaySignature) {
		super();
		this.orderId = orderId;
		this.razorpayOrderId = razorpayOrderId;
		this.razorpayPaymentId = razorpayPaymentId;
		this.razorpaySignature = razorpaySignature;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getRazorpaySignature() {
		return razorpaySignature;
	}

	public void setRazorpaySignature(String razorpaySignature) {
		this.razorpaySignature = razorpaySignature;
	}

	@Override
	public String toString() {
		return "VerifyPaymentRequest [orderId=" + orderId + ", razorpayOrderId=" + razorpayOrderId
				+ ", razorpayPaymentId=" + razorpayPaymentId + ", razorpaySignature=" + razorpaySignature + "]";
	}

}
