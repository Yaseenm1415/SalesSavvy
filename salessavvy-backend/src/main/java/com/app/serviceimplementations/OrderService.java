package com.app.serviceimplementations;

import com.app.controllers.ProductImageController;
import com.app.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.app.dto.OrderItemResponse;
import com.app.dto.OrderResponse;
import com.app.dto.PlaceOrderRequest;
import com.app.entities.Cart;
import com.app.entities.Order;
import com.app.entities.OrderItem;
import com.app.entities.Product;
import com.app.entities.ProductImage;
import com.app.entities.User;
import com.app.enums.OrderStatus;
import com.app.exceptions.AccesDeniedException;
import com.app.exceptions.NotFoundException;
import com.app.exceptions.OutOfStockException;
import com.app.repositories.CartRepository;
import com.app.repositories.OrderItemRepository;
import com.app.repositories.OrderRepository;
import com.app.repositories.ProductImageRepository;
import com.app.services.OrderServiceContract;
import jakarta.transaction.Transactional;

@Service
public class OrderService implements OrderServiceContract {
	private final ProductRepository productRepository;
	private final ProductImageRepository productImageRepository;
	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartRepository cartRepository;

	public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			CartRepository cartRepository, ProductRepository productRepository, ProductImageRepository productImageRepository, ProductImageController productImageController) {
		super();
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
	}

	@Transactional
	@Override
	public OrderResponse placeOrder(User user, PlaceOrderRequest request) {
		List<Cart> cartItems = cartRepository.findAllById(request.getCartItemsIds());

		if (cartItems.isEmpty()) {
			throw new NotFoundException("No cart items selected");
		}

		BigDecimal totalAmount = BigDecimal.ZERO;
		for (Cart cart : cartItems) {
			if (cart.getUser().getUserId() != user.getUserId()) {
				throw new AccesDeniedException("Unauthorized access");
			}

			Product product = cart.getProduct();

			if (cart.getQuantity() > product.getStock()) {
				throw new OutOfStockException(product.getName() + " is out of stock");
			}

			BigDecimal subtotal = cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));

			totalAmount = totalAmount.add(subtotal);
		}

		Order savedOrder = new Order();
		savedOrder.setUser(user);
		savedOrder.setTotalAmount(totalAmount);
		savedOrder.setStatus(OrderStatus.PENDING);
		savedOrder = orderRepository.save(savedOrder);

		List<OrderItemResponse> itemResponses = new ArrayList<>();
		for (Cart cart : cartItems) {
			Product product = cart.getProduct();
			BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));
			
			OrderItem item = new OrderItem();
			item.setOrder(savedOrder);
			item.setProduct(product);
			item.setQuantity(cart.getQuantity());
			item.setPriceAtPurchase(product.getPrice());
			item.setSubtotal(subtotal);
			orderItemRepository.save(item);
			
			Optional<ProductImage> image = productImageRepository.findFirstByProductProductId(product.getProductId());
			String imageUrl = image != null ? image.get().getImageUrl() : null;
			product.setStock(product.getStock() - cart.getQuantity());
			productRepository.save(product);

			itemResponses.add(new OrderItemResponse(product.getProductId(), product.getName(), cart.getQuantity(),
					product.getPrice(), subtotal, imageUrl));

			cartRepository.delete(cart);
		}

		return new OrderResponse(savedOrder.getOrderId(), savedOrder.getUser().getUsername(), savedOrder.getUser().getEmail(),savedOrder.getTotalAmount(), savedOrder.getStatus().name(),
				savedOrder.getCreatedAt(), itemResponses);

	}

	@Override
	public List<OrderResponse> getOrders(User user) {
		List<Order> orders = orderRepository.findByUser(user);

		List<OrderResponse> orderResponses = new ArrayList<>();

		for (Order order : orders) {
			List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

			List<OrderItemResponse> itemResponses = new ArrayList<>();
			for (OrderItem orderItem : orderItems) {
				Product product = orderItem.getProduct();
				Optional<ProductImage> image = productImageRepository.findFirstByProductProductId(product.getProductId());
				String imageUrl = image.isPresent()? image.get().getImageUrl() : null;
				itemResponses.add(
						new OrderItemResponse(orderItem.getProduct().getProductId(), orderItem.getProduct().getName(),
								orderItem.getQuantity(), orderItem.getPriceAtPurchase(), orderItem.getSubtotal(), imageUrl

						));
			}

			orderResponses.add(new OrderResponse(order.getOrderId(), order.getUser().getUsername(), order.getUser().getEmail(),order.getTotalAmount(), order.getStatus().name(),
					order.getCreatedAt(), itemResponses));
		}
		return orderResponses;
	}

	@Override
	public OrderResponse getOrderById(int orderId, User user) {
		Order order = orderRepository.findByOrderIdAndUser(orderId, user)
				.orElseThrow(() -> new NotFoundException("Order not found"));

		List<OrderItem> items = orderItemRepository.findByOrder(order);

		List<OrderItemResponse> itemResponses = new ArrayList<>();

		for (OrderItem item : items) {
			
			Product product = item.getProduct();
			Optional<ProductImage> image = productImageRepository.findFirstByProductProductId(product.getProductId());
			String imageUrl = image.isPresent()? image.get().getImageUrl() : null;
			itemResponses.add(new OrderItemResponse(item.getProduct().getProductId(), item.getProduct().getName(),
					item.getQuantity(), item.getPriceAtPurchase(), item.getSubtotal(), imageUrl));
		}

		return new OrderResponse(order.getOrderId(), order.getUser().getUsername(), order.getUser().getEmail(),order.getTotalAmount(), order.getStatus().name(),
				order.getCreatedAt(), itemResponses);
	}

	@Override
	public List<OrderResponse> getAllOrders() {

		List<Order> orders = orderRepository.findAll();

		List<OrderResponse> orderResponses = new ArrayList<>();

		for (Order order : orders) {
			List<OrderItem> orderItems = orderItemRepository.findByOrder(order);

			List<OrderItemResponse> itemResponses = new ArrayList<>();
			for (OrderItem orderItem : orderItems) {
				Product product = orderItem.getProduct();
				Optional<ProductImage> image = productImageRepository.findFirstByProductProductId(product.getProductId());
				String imageUrl = image.isPresent() ? image.get().getImageUrl() : null;
				itemResponses.add(
						new OrderItemResponse(orderItem.getProduct().getProductId(), orderItem.getProduct().getName(),
								orderItem.getQuantity(), orderItem.getPriceAtPurchase(), orderItem.getSubtotal(), imageUrl

						));
			}

			orderResponses.add(new OrderResponse(order.getOrderId(), order.getUser().getUsername(), order.getUser().getEmail(),order.getTotalAmount(), order.getStatus().name(),
					order.getCreatedAt(), itemResponses));
		}
		return orderResponses;
	}

	@Override
	public OrderResponse updateOrderStatus(int orderId, OrderStatus status) {
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
		order.setStatus(status);
		orderRepository.save(order);

		List<OrderItem> orderItems = orderItemRepository.findByOrder(order);
		List<OrderItemResponse> itemResponses = new ArrayList<>();
		for (OrderItem item : orderItems) {
			Product product = item.getProduct();
			Optional<ProductImage> image = productImageRepository.findFirstByProductProductId(product.getProductId());
			String imageUrl = image.isPresent()? image.get().getImageUrl() : null;
			itemResponses.add(new OrderItemResponse(item.getProduct().getProductId(), item.getProduct().getName(),
					item.getQuantity(), item.getPriceAtPurchase(), item.getSubtotal(), imageUrl));
		}

		return new OrderResponse(order.getOrderId(), order.getUser().getUsername(), order.getUser().getEmail(),order.getTotalAmount(), order.getStatus().name(),
				order.getCreatedAt(), itemResponses);
	}

	@Override
	public OrderResponse getOrderByIdForAdmin(int orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new NotFoundException("Order not found"));

		List<OrderItem> items = orderItemRepository.findByOrder(order);

		List<OrderItemResponse> itemResponses = new ArrayList<>();

		for (OrderItem item : items) {
			
			Product product = item.getProduct();
			Optional<ProductImage> image = productImageRepository.findFirstByProductProductId(product.getProductId());
			String imageUrl = image.isPresent()? image.get().getImageUrl() : null;
			itemResponses.add(new OrderItemResponse(item.getProduct().getProductId(), item.getProduct().getName(),
					item.getQuantity(), item.getPriceAtPurchase(), item.getSubtotal(), imageUrl));
		}

		return new OrderResponse(order.getOrderId(), order.getUser().getUsername(), order.getUser().getEmail(),order.getTotalAmount(), order.getStatus().name(),
				order.getCreatedAt(), itemResponses);

	}
	
	@Override
	public void cancelOrder(int orderId, int userId) {

	    Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    if (order.getUser().getUserId() != userId) {
	        throw new RuntimeException("Unauthorized");
	    }

	    if (order.getStatus() != OrderStatus.PENDING &&
	        order.getStatus() != OrderStatus.CONFIRMED) {

	        throw new RuntimeException("Order cannot be cancelled.");
	    }

	    order.setStatus(OrderStatus.CANCELLED);

	    orderRepository.save(order);

	    
	}

}
