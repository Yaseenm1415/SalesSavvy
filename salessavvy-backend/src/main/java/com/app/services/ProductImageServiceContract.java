package com.app.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.app.entities.ProductImage;

public interface ProductImageServiceContract {
	ProductImage uploadImage(int productId, MultipartFile file);
	List<ProductImage> getProductImage(int productId);
	void deleteImage(int imageId);
}
