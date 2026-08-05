package com.app.dto;

import java.math.BigDecimal;
import java.util.List;

import com.app.entities.Category;

public class ProductResponse {
	private int productId;
	private String name;
	private String description;
	private BigDecimal price;
	private int stock;
	private Category category;
	private List<ProductImageResponse> images;

	public ProductResponse() {
		super();
		
	}

	public ProductResponse(int productId, String name, String description, BigDecimal price, int stock,
			Category category, List<ProductImageResponse> images) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.category = category;
		this.images = images;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductImageResponse> getImages() {
		return images;
	}

	public void setImages(List<ProductImageResponse> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "ProductResponse [productId=" + productId + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", stock=" + stock + ", category=" + category + ", images=" + images + "]";
	}

	
	
	
	}
