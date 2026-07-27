package com.app.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	Optional<Product> findByName(String name);
	Page<Product> findByCategoryCategoryId(int categoryId, Pageable pageable);
	Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Product> findByNameContainingIgnoreCaseAndCategoryCategoryId(String keyword, int categoryId, Pageable pageable);
	
	
	
	
}
