package com.app.dto;

public class ProductImageResponse {
	private int imageId;
	private String imageUrl;

	public ProductImageResponse() {
		super();
	}

	public ProductImageResponse(int imageId, String imageUrl) {
		super();
		this.imageId = imageId;
		this.imageUrl = imageUrl;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "ProductImageResponse [imageId=" + imageId + ", imageUrl=" + imageUrl + "]";
	}

}
