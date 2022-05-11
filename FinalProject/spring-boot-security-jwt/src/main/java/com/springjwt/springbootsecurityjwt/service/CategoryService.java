package com.springjwt.springbootsecurityjwt.service;

import com.springjwt.springbootsecurityjwt.feignclients.BlogFeignClient;
import com.springjwt.springbootsecurityjwt.payload.request.BlogRequest;
import com.springjwt.springbootsecurityjwt.payload.request.CategoryRequest;
import com.springjwt.springbootsecurityjwt.repository.CategoryRepository;
import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import com.springjwt.springbootsecurityjwt.response.CategoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import com.springjwt.springbootsecurityjwt.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.*;

@Service
public class CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    BlogFeignClient feignClient;

    @Autowired
    CategoryRepository categoryRepository;

    @CircuitBreaker(name = "categoryService",
            fallbackMethod = "fallbackGetCategories")
    public List<CategoryResponse> getCategories () {
        List<CategoryResponse> categories =feignClient.getCategories();

        return categories;
    }

    public List<CategoryResponse> fallbackGetCategories (Throwable th) {
        logger.error("Error = " + th);
        List<CategoryResponse> categories=new ArrayList<CategoryResponse>();
        return categories;
    }

    @CircuitBreaker(name = "categoryService",
            fallbackMethod = "fallbackGetCategoryById")
    public ResponseEntity<CategoryResponse> getCategoryById (String id) {
        ResponseEntity<CategoryResponse> category=feignClient.getCategoryById(id);
        return category;
    }

    public ResponseEntity<CategoryResponse> fallbackGetCategoryById (String id,Throwable th) {
        logger.error("Error = " + th);
        return ResponseEntity.ok().body(new CategoryResponse());
    }

    @CircuitBreaker(name = "categoryService",
            fallbackMethod = "fallbackCreateCategory")
    public CategoryResponse createCategory (CategoryRequest categoryRequest) {
        CategoryResponse category=feignClient.createCategory(categoryRequest);
        return category;
    }

    public CategoryResponse fallbackCreateCategory (CategoryRequest categoryRequest, Throwable th) {
        logger.error("Error = " + th);
        return new CategoryResponse();
    }

    @CircuitBreaker(name = "categoryService",
            fallbackMethod = "fallbackGetCategoryAfterDeleting")
    public Map<String, Boolean> getCategoryAfterDeleting (String id) {
        feignClient.getCategoryAfterDeleting(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> fallbackGetCategoryAfterDeleting (String id,Throwable th) {
        logger.error("Error = " + th);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.FALSE);
        return response;
    }
    @CircuitBreaker(name = "categoryService",
            fallbackMethod = "fallbackUpdateCategory")
    public ResponseEntity<CategoryResponse> updateCategory (CategoryRequest categoryRequest, String id) {
        ResponseEntity<CategoryResponse> category=feignClient.updateCategory(categoryRequest,id);
        return category;
    }

    public ResponseEntity<CategoryResponse> fallbackUpdateCategory (CategoryRequest categoryRequest, String id,Throwable th) {
        logger.error("Error = " + th);
        return ResponseEntity.ok().body(new CategoryResponse());
    }

    //Methods to establish category-user relationship in SqlDB
    public void saveCategories (Category category) {
       categoryRepository.save(category);
    }

    public Optional<Category> getCategoryFromDB(String name)
    {
        Optional<Category> category=categoryRepository.findByName(name);
        return category;
    }

}
