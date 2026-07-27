package com.app.dto;

import java.math.BigDecimal;

public class ProductRequest {
	private String name;
	private String description;
	private BigDecimal price;
	private int stock;
	private int categoryId;

	public ProductRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductRequest(String name, String description, BigDecimal price, int stock, int categoryId) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
