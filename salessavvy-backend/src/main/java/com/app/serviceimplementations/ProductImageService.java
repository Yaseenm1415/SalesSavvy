package com.app.serviceimplementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ProductImageService implements ProductImageServiceContract {

	private final ProductImageRepository productImageRepository;
	private final ProductRepository productRepository;
	private final Cloudinary cloudinary;
	

	public ProductImageService(ProductImageRepository productImageRepository, ProductRepository productRepository,
			Cloudinary cloudinary) {
		super();
		this.productImageRepository = productImageRepository;
		this.productRepository = productRepository;
		this.cloudinary = cloudinary;
	}

	@Override
	public List<ProductImage> uploadImages(int productId, MultipartFile[] files) {

	    Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new NotFoundException("Product not found"));

	    List<ProductImage> uploadedImages = new ArrayList<>();

	    for (MultipartFile file : files) {

	        try {

	            Map<?, ?> uploadResult = cloudinary.uploader().upload(
	                    file.getBytes(),
	                    ObjectUtils.emptyMap()
	            );

	            String imageUrl = uploadResult.get("secure_url").toString();
	            String publicId = uploadResult.get("public_id").toString();
	            
	            ProductImage image = new ProductImage();
	            image.setProduct(product);
	            image.setImageUrl(imageUrl);
	            image.setPublicId(publicId);

	            uploadedImages.add(productImageRepository.save(image));

	        } catch (IOException e) {
	            throw new FailedException("Failed to upload image: " + file.getOriginalFilename());
	        }
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

	    try {
	        cloudinary.uploader().destroy(
	                image.getPublicId(),
	                ObjectUtils.emptyMap()
	        );
	    } catch (IOException e) {
	        throw new FailedException("Failed to delete image from Cloudinary");
	    }

	    productImageRepository.delete(image);
	}

}
