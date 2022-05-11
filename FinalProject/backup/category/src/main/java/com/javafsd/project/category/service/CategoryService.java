package com.javafsd.project.category.service;

import com.javafsd.project.category.exception.ResourceNotFoundException;
import com.javafsd.project.category.model.Category;
import com.javafsd.project.category.repository.CategoryRepository;
import com.javafsd.project.category.request.CategoryRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest categoryRequest) {
        Category category=new Category();
        category.setName(categoryRequest.getName());
        category.setCreatedAt();

        return categoryRepository.save(category);

    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public ResponseEntity< Category > getCategoryById(String id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category not found for this id :: " + id));
        return ResponseEntity.ok().body(category);
    }

    public Map<String, Boolean> deleteCategory(String id) throws ResourceNotFoundException
    {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));

        categoryRepository.delete(category);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public ResponseEntity<Category> updateCategory(CategoryRequest categoryRequest, String id) throws ResourceNotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found for this id :: " + id));

        category.setName(categoryRequest.getName());
        category.setCreatedAt();

        final Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }
}
