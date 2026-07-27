package com.app.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.entities.Category;
import com.app.services.CategoryServiceContract;

@RestController
public class CategoryController {

	private CategoryServiceContract categoryService;

	public CategoryController(CategoryServiceContract categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping("/api/categories")
	public ResponseEntity<?> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

	@GetMapping("/api/categories/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable int id) {

		return ResponseEntity.ok(categoryService.getCategoryById(id));

	}

	@PostMapping("/admin/categories")
	public ResponseEntity<?> createCategory(@RequestBody Category category) {

		return ResponseEntity.ok(categoryService.createCategory(category));

	}

	@PutMapping("/admin/categories/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category category) {

		return ResponseEntity.ok(categoryService.updateCategory(id, category));

	}

	@DeleteMapping("/admin/categories/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable int id) {

		categoryService.deleteCategory(id);
		return ResponseEntity.ok(Map.of("success", "Category deleted successfully"));

	}
}
