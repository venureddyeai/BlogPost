package com.javafsd.project.category.service;

import com.javafsd.project.category.exception.ResourceNotFoundException;
import com.javafsd.project.category.model.Category;
import com.javafsd.project.category.repository.CategoryRepository;
import com.javafsd.project.category.request.CategoryRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private Category category;
    private CategoryRequest categoryRequest;
    private static final String ID = "123";
    private List<Category> categoryList;
    LocalDateTime presentTime = LocalDateTime.now();

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
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
    void create() {
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);
        Category createdCategory = categoryService.createCategory(categoryRequest);
        verify(categoryRepository, times(1)).save(Mockito.any(Category.class));
        assertEquals(category.getId(), createdCategory.getId());
    }

    @Test
    void read() throws ResourceNotFoundException {
        when(categoryRepository.findById(Mockito.any(String.class))).thenReturn(Optional.ofNullable(category));
        ResponseEntity<Category> response = categoryService.getCategoryById(ID);
        assertNotNull(response.getBody());
    }

    @Test
    void readAll() {
        when(categoryRepository.findAll()).thenReturn(categoryList);
        categoryList = categoryService.getCategories();
        assertTrue(!categoryList.isEmpty());
    }

    @Test
    void update() throws ResourceNotFoundException {
        Category newCategory = new Category(ID, "Travell1", presentTime);
        when(categoryRepository.findById(ID)).thenReturn(Optional.ofNullable(category));
        when(categoryRepository.save(category)).thenReturn(newCategory);
        ResponseEntity<Category> response = categoryService.updateCategory(categoryRequest, ID);
        verify(categoryRepository, times(1)).save(Mockito.any(Category.class));
        assertNotNull(response.getBody());
    }

    @Test
    void delete() throws ResourceNotFoundException {
        Mockito.lenient().when(categoryRepository.findById(ID)).thenReturn(Optional.ofNullable(category));
        categoryService.deleteCategory(ID);
    }
}