package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Cart;
import com.app.entities.Product;
import com.app.entities.User;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	Optional<Cart> findByUserAndProduct(User user, Product product);
	List<Cart> findByUser(User user);
}
