package com.app.services;

import java.util.List;

import com.app.entities.Category;

public interface CategoryServiceContract {
	Category createCategory(Category category);
	Category updateCategory(int categoryId, Category category);
	List<Category> getAllCategories();
	Category getCategoryById(int categoryId);
	void deleteCategory(int categoryId);
}
