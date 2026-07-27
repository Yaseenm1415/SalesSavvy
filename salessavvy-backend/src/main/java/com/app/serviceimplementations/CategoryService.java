package com.app.serviceimplementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.entities.Category;
import com.app.exceptions.AlreadyExistsException;
import com.app.exceptions.NotFoundException;
import com.app.repositories.CategoryRepository;
import com.app.services.CategoryServiceContract;
@Service
public class CategoryService implements CategoryServiceContract{

	private CategoryRepository categoryRepository;
	
	
	public CategoryService(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category createCategory(Category category) {
		
		if(categoryRepository.existsByCategoryName(category.getCategoryName())) {
			throw new AlreadyExistsException("Category already exists");
		} 
		return categoryRepository.save(category);
	}

	@Override
	public Category updateCategory(int categoryId, Category category) {
		Category existingCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
		
		if(categoryRepository.existsByCategoryName(category.getCategoryName())) {
			throw new AlreadyExistsException("Category already exists");
		}
		existingCategory.setCategoryName(category.getCategoryName());
		return categoryRepository.save(existingCategory);
	}

	@Override
	public List<Category> getAllCategories() {
		
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(int categoryId) {
		
		return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("Category not found"));
	}

	@Override
	public void deleteCategory(int categoryId) {
		categoryRepository.deleteById(categoryId);
		
	}

}
