package com.javafsd.project.category.controller;

import com.javafsd.project.category.exception.ResourceNotFoundException;
import com.javafsd.project.category.model.Category;
import com.javafsd.project.category.request.CategoryRequest;
import com.javafsd.project.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public Category createCategory(@RequestBody @Valid CategoryRequest categoryRequest)
    {
        return categoryService.createCategory(categoryRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById (@PathVariable String id) throws ResourceNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<Category> getCategories(){
        List<Category> categories=categoryService.getCategories();
        return categories;
    }

   @DeleteMapping("/{id}")
    public Map<String, Boolean> getCategoryAfterDeleting(@PathVariable String id) throws ResourceNotFoundException {
        return categoryService.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody @Valid CategoryRequest category, @PathVariable String id) throws ResourceNotFoundException{
        return categoryService.updateCategory(category,id);
    }


}
