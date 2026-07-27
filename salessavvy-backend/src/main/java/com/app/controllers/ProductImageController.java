package com.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.services.ProductImageServiceContract;

@RestController
public class ProductImageController {
	private final ProductImageServiceContract productImageService;

	public ProductImageController(ProductImageServiceContract productImageService) {
		super();
		this.productImageService = productImageService;
	}

	@PostMapping("/admin/products/{productId}/images")
	public ResponseEntity<?> uploadImage(@PathVariable int productId, @RequestParam("file") MultipartFile file) {

		return ResponseEntity.ok(productImageService.uploadImage(productId, file));

	}

	@GetMapping("/api/products/{productId}/images")
	public ResponseEntity<?> getProductImages(@PathVariable int productId) {

		return ResponseEntity.ok(productImageService.getProductImage(productId));

	}

	@DeleteMapping("/admin/product-images/{imageId}")
	public ResponseEntity<?> deleteImage(@PathVariable int imageId) {

		productImageService.deleteImage(imageId);

		return ResponseEntity.ok(Map.of("message", "image deleted successfully"));

	}

}
