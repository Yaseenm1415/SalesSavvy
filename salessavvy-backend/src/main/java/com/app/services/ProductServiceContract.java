package com.app.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.dto.ProductRequest;
import com.app.dto.ProductResponse;
import com.app.entities.Product;

public interface ProductServiceContract {
	Product createProduct(ProductRequest request);
	Product updateProduct(int productId, ProductRequest request);
	void deleteProduct(int productId);
	Page<ProductResponse> getAllProducts(String keyword, Integer categoryId, Pageable pageable);
	ProductResponse getProductById(int productId);
	
}
