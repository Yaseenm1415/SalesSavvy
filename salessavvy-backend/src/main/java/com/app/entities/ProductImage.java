package com.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_images")
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int imageId;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@Column
	private String imageUrl;
	@Column
	private String publicId;

	public ProductImage() {
		super();
	}

	public ProductImage(int imageId, Product product, String imageUrl, String publicId) {
		super();
		this.imageId = imageId;
		this.product = product;
		this.imageUrl = imageUrl;
		this.publicId = publicId;
	}

	public ProductImage(Product product, String imageUrl, String publicId) {
		super();
		this.product = product;
		this.imageUrl = imageUrl;
		this.publicId = publicId;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}

	@Override
	public String toString() {
		return "ProductImage [imageId=" + imageId + ", product=" + product + ", imageUrl=" + imageUrl + ", publicId="
				+ publicId + "]";
	}

}
