package com.app.dto;

public class RazorpayOrderResponse {

	private String razorpayOrderId;
	private String key;
	private long amount;
	private String currency;

	public RazorpayOrderResponse() {
		super();
	}

	public RazorpayOrderResponse(String razorpayOrderId, String key, long amount, String currency) {
		super();
		this.razorpayOrderId = razorpayOrderId;
		this.key = key;
		this.amount = amount;
		this.currency = currency;
	}

	public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "RazorpayOrderResponse [razorpayOrderId=" + razorpayOrderId + ", key=" + key + ", amount=" + amount
				+ ", currency=" + currency + "]";
	}

}
