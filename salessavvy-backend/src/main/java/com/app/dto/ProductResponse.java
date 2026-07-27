package com.app.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponse {
	private int productId;
	private String name;
	private String description;
	private BigDecimal price;
	private int stock;
	private String categoryName;
	private List<String> imageUrls;

	public ProductResponse() {
		super();
		
	}

	public ProductResponse(int productId, String name, String description, BigDecimal price, int stock,
			String categoryName, List<String>  imageUrls) {
		super();
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
		this.categoryName = categoryName;
		this.imageUrls = imageUrls;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String>  getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String>  imageUrl) {
		this.imageUrls = imageUrl;
	}

	@Override
	public String toString() {
		return "ProductResponse [productId=" + productId + ", name=" + name + ", description=" + description
				+ ", price=" + price + ", stock=" + stock + ", categoryName=" + categoryName + ", imageUrls=" + imageUrls
				+ "]";
	}

	

}
