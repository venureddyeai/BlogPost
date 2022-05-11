package com.springjwt.springbootsecurityjwt.controllers;


import com.springjwt.springbootsecurityjwt.response.CategoryResponse;
import com.springjwt.springbootsecurityjwt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasRole('BLOGGER')  or hasRole('ADMIN')")
    public List<CategoryResponse> getCategories(){
       return categoryService.getCategories();
    }
}
