package com.app.serviceimplementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.ProductRequest;
import com.app.dto.ProductResponse;
import com.app.entities.Category;
import com.app.entities.Product;
import com.app.entities.ProductImage;
import com.app.exceptions.NotFoundException;
import com.app.repositories.CategoryRepository;
import com.app.repositories.ProductImageRepository;
import com.app.repositories.ProductRepository;
import com.app.services.ProductServiceContract;
@Service
public class ProductService implements ProductServiceContract{

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	private ProductImageRepository productImageRepository;
	
	
	
	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
			ProductImageRepository productImageRepository) {
		super();
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.productImageRepository = productImageRepository;
	}


	@Override
	public Product createProduct(ProductRequest request) {
		Category category = categoryRepository.findById(request.getCategoryId())
							.orElseThrow(() -> new NotFoundException("Category not found"));
		
		Product product = new Product();
		
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		product.setCategory(category);
		
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(int productId, ProductRequest request) {
		Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product not found"));
		
		Category category = categoryRepository.findById(request.getCategoryId())
				.orElseThrow(() -> new NotFoundException("Category not found"));
		
		existingProduct.setName(request.getName());
		existingProduct.setDescription(request.getDescription());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setStock(request.getStock());
		existingProduct.setCategory(category);
		return productRepository.save(existingProduct);
	}
	
	@Transactional
	@Override
	public void deleteProduct(int productId) {
		Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product not found"));
		
		List<ProductImage> images = productImageRepository.findByProductProductId(productId);
		
		for(ProductImage image : images) {
			Path path = Paths.get(image.getImageUrl().replaceFirst("^/", ""));
			
			try {
				Files.deleteIfExists(path);
			} catch (IOException e) {
				throw new NotFoundException("Failed to delete image file: " + image.getImageUrl());
			}
			
			productImageRepository.delete(image);
		}
		productRepository.delete(product);
		
	}

	@Override
	public Page<ProductResponse> getAllProducts(String keyword, Integer categoryId, Pageable pageable) {
		Page<Product> products;
		
		boolean hasKeyword = keyword != null && !keyword.isBlank();
		
		if(!hasKeyword && categoryId == null) {
			products = productRepository.findAll(pageable);
		} else if (hasKeyword && categoryId == null) {
			products = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
		} else if (!hasKeyword && categoryId != null) {
			products = productRepository.findByCategoryCategoryId(categoryId, pageable);
		} else {
			products = productRepository.findByNameContainingIgnoreCaseAndCategoryCategoryId(keyword, categoryId, pageable);
		}
				
		return products.map(product -> {
			List<ProductImage> images = productImageRepository.findByProductProductId(product.getProductId());
			ArrayList<String> imageUrls = new ArrayList<>();
			 for(ProductImage image : images) {
				 imageUrls.add(image.getImageUrl());
			 }
			return new ProductResponse(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCategory().getCategoryName(), imageUrls);
		});
		
	}

	@Override
	public ProductResponse getProductById(int productId) {
		Product product =  productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
		List<ProductImage> images = productImageRepository.findByProductProductId(product.getProductId());
		ArrayList<String> imageUrls = new ArrayList<>();
		 for(ProductImage image : images) {
			 imageUrls.add(image.getImageUrl());
		 }
		return new ProductResponse(product.getProductId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCategory().getCategoryName(), imageUrls);
	}

	
	
	

}
