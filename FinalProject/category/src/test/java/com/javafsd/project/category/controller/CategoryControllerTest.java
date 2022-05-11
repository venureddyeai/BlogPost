package com.javafsd.project.category.controller;

import com.javafsd.project.category.exception.ResourceNotFoundException;
import com.javafsd.project.category.model.Category;
import com.javafsd.project.category.repository.CategoryRepository;
import com.javafsd.project.category.request.CategoryRequest;
import com.javafsd.project.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    private Category category;
    private CategoryRequest categoryRequest;
    private static final String ID = "123";
    private List<Category> categoryList;
    LocalDateTime presentTime = LocalDateTime.now();

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;


    @BeforeEach
    void setUp() {
        category = new Category(ID, "Travell", presentTime);
        categoryRequest = new CategoryRequest();
        categoryRequest.setName(category.getName());
        categoryList = createCategoryList(category);
    }

    private List<Category> createCategoryList(Category category) {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(ID, "Travell", presentTime));
        return categoryList;
    }


    @Test
    void createCategory() {
        when(categoryService.createCategory(categoryRequest)).thenReturn(category);
        category = categoryController.createCategory(categoryRequest);
        assertEquals(categoryRequest.getName(),category.getName());
      }

    @Test
    void getCategoryById() throws ResourceNotFoundException {
        ResponseEntity<Category> response = ResponseEntity.ok().body(category);
        when(categoryService.getCategoryById(ID)).thenReturn(response);
        response = categoryController.getCategoryById(ID);
        assertNotNull(response.getBody());
    }

    @Test
    void getCategories() {
        when(categoryService.getCategories()).thenReturn(categoryList);
        categoryList = categoryController.getCategories();
        assertTrue(!categoryList.isEmpty());

    }

    @Test
    void getCategoryAfterDeleting() throws ResourceNotFoundException {
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        when(categoryService.deleteCategory(ID)).thenReturn(response);
        response = categoryController.getCategoryAfterDeleting(ID);
        assertTrue(response.get("deleted"));
    }

    @Test
    void updateCategory() throws ResourceNotFoundException {
        ResponseEntity<Category> response = ResponseEntity.ok().body(category);
        when(categoryService.updateCategory(categoryRequest,ID)).thenReturn(response);
        response = categoryController.updateCategory(categoryRequest,ID);
        assertTrue(response.getStatusCode().is2xxSuccessful());

    }
}