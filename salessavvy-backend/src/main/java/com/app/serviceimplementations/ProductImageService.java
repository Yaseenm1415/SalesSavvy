package com.app.serviceimplementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.entities.Product;
import com.app.entities.ProductImage;
import com.app.exceptions.FailedException;
import com.app.exceptions.NotFoundException;
import com.app.repositories.ProductImageRepository;
import com.app.repositories.ProductRepository;
import com.app.services.ProductImageServiceContract;

@Service
public class ProductImageService implements ProductImageServiceContract {

	ProductImageRepository productImageRepository;
	ProductRepository productRepository;

	public ProductImageService(ProductImageRepository productImageRepository, ProductRepository productRepository) {
		super();
		this.productImageRepository = productImageRepository;
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductImage>  uploadImages(int productId, MultipartFile[] files) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("product not found"));

		String uploadDi = "uploads/";
		
		List<ProductImage> uploadedImages = new ArrayList<>();
		
		for(MultipartFile file : files) {
			
		
		String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

		Path path = Paths.get(uploadDi, fileName);

		try {
			Files.createDirectories(path.getParent());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FailedException("Failed to save image: " + file.getOriginalFilename());
		}

		ProductImage image = new ProductImage();

		image.setProduct(product);
		image.setImageUrl("/uploads/" + fileName);
		uploadedImages.add(productImageRepository.save(image));
	}
		return uploadedImages;

	}

	@Override
	public List<ProductImage> getProductImage(int productId) {
		if (!productRepository.existsById(productId)) {
			throw new NotFoundException("Product not found");
		}
		return productImageRepository.findByProductProductId(productId);
	}

	@Transactional
	@Override
	public void deleteImage(int imageId) {
		ProductImage image = productImageRepository.findById(imageId)
				.orElseThrow(() -> new NotFoundException("Image not found"));

		String imagePath = image.getImageUrl();

		Path filePath = Paths.get(imagePath.replaceFirst("^/", ""));

		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			throw new NotFoundException("Failed to delete image file: " + imagePath);
		}

		productImageRepository.delete(image);

	}

}
