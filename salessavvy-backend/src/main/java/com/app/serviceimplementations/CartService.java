package com.app.serviceimplementations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.app.dto.CartRequest;
import com.app.dto.CartResponse;
import com.app.entities.Cart;
import com.app.entities.Product;
import com.app.entities.ProductImage;
import com.app.entities.User;
import com.app.exceptions.AuthenticationException;
import com.app.exceptions.InvalidQuantityException;
import com.app.exceptions.NotFoundException;
import com.app.exceptions.OutOfStockException;
import com.app.repositories.CartRepository;
import com.app.repositories.ProductImageRepository;
import com.app.repositories.ProductRepository;
import com.app.services.CartServiceContract;

@Service
public class CartService implements CartServiceContract {
	private CartRepository cartRepository;
	private ProductRepository productRepository;
	private ProductImageRepository productImageRepository;

	public CartService(CartRepository cartRepository, ProductRepository productRepository,
			ProductImageRepository productImageRepository) {
		super();
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
	}

	@Override
	public CartResponse addToCart(User user, CartRequest request) {

		if (request.getQuantity() <= 0) {
			throw new InvalidQuantityException("Quantity must be greater than 0");
		}

		Product product = productRepository.findById(request.getProductId())
				.orElseThrow(() -> new NotFoundException("Product not found"));

		if (request.getQuantity() > product.getStock()) {
			throw new OutOfStockException("Insufficient stock available");
		}

		Optional<Cart> existingCart = cartRepository.findByUserAndProduct(user, product);

		if (existingCart.isPresent()) {
			Cart cart = existingCart.get();
			if (cart.getQuantity() + request.getQuantity() > product.getStock()) {
				throw new OutOfStockException("Insufficient stock available");
			}
			cart.setQuantity(cart.getQuantity() + request.getQuantity());
			cartRepository.save(cart);
			
			
		
		} else {
			Cart cart = new Cart();

			cart.setUser(user);
			cart.setProduct(product);
			cart.setQuantity(request.getQuantity());
			cartRepository.save(cart);
		
		}
		
		Cart cart = cartRepository.findByUserAndProduct(user, product).orElseThrow(() -> new NotFoundException("cart not found"));
		BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
		
		ProductImage image = productImageRepository.findFirstByProductProductId(product.getProductId())
				.orElse(null);

		return new CartResponse(cart.getCartId(), product.getProductId(), product.getName(), product.getPrice(),
				cart.getQuantity(), subtotal, image != null ? image.getImageUrl() : null);	
	}

	@Override
	public List<CartResponse> getCartItems(User user) {

		List<Cart> cartItems = cartRepository.findByUser(user);

		return cartItems.stream().map(cart -> {
			Product product = cart.getProduct();

			BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));

			ProductImage image = productImageRepository.findFirstByProductProductId(product.getProductId())
					.orElse(null);

			return new CartResponse(cart.getCartId(), product.getProductId(), product.getName(), product.getPrice(),
					cart.getQuantity(), subtotal, image != null ? image.getImageUrl() : null);
		}).toList();

	}

	@Override
	public CartResponse updateQuantity(int cartId, int quantity, User user) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart item not found"));

		if (cart.getUser().getUserId() != user.getUserId()) {
			throw new AuthenticationException("Unauthorized");
		}

		if (quantity <= 0) {
			throw new InvalidQuantityException("Quantity must be greater than 0");
		}

		Product product = cart.getProduct();

		if (quantity > product.getStock()) {
			throw new OutOfStockException("Insufficient stock available");
		}

		cart.setQuantity(quantity);
		cartRepository.save(cart);
		
		BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));

		ProductImage image = productImageRepository.findFirstByProductProductId(product.getProductId())
				.orElse(null);

		return new CartResponse(cart.getCartId(), product.getProductId(), product.getName(), product.getPrice(),
				cart.getQuantity(), subtotal, image != null ? image.getImageUrl() : null);
	}

	@Override
	public void removeFromCart(int cartId, User user) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("Cart item not found"));

		if (cart.getUser().getUserId() != user.getUserId()) {
			throw new AuthenticationException("Unauthorized");
		}

		cartRepository.delete(cart);

	}

}
