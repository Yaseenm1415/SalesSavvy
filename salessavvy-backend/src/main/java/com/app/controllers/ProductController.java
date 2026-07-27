package com.app.controllers;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ProductRequest;
import com.app.services.ProductServiceContract;

@RestController
public class ProductController {
	private final ProductServiceContract productService;

	public ProductController(ProductServiceContract productService) {
		super();
		this.productService = productService;
	}

	@PostMapping("/admin/products")
	public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {

		return ResponseEntity.ok(productService.createProduct(request));

	}

	@PutMapping("/admin/products/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable int productId, @RequestBody ProductRequest request) {

		return ResponseEntity.ok(productService.updateProduct(productId, request));

	}

	@DeleteMapping("/admin/products/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable int productId) {

		productService.deleteProduct(productId);

		return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));

	}

	@GetMapping("/api/products")
	public ResponseEntity<?> getAllProducts(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer categoryId, Pageable pageable) {

		return ResponseEntity.ok(productService.getAllProducts(keyword, categoryId, pageable));

	}

	@GetMapping("/api/products/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable int productId) {

		return ResponseEntity.ok(productService.getProductById(productId));

	}

}
