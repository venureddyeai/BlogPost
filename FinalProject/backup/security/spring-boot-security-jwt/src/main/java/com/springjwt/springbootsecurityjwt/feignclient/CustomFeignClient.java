package com.springjwt.springbootsecurityjwt.feignclient;

import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import com.springjwt.springbootsecurityjwt.response.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@org.springframework.cloud.openfeign.FeignClient(value = "api-gateway")
public interface CustomFeignClient {
    @GetMapping("blog-service/blogs")
    public List<BlogResponse> getBlogs();

    @GetMapping("blog-service/blogs/{id}")
    public ResponseEntity<BlogResponse> getBlogById(String id);

    @PostMapping("blog-service/blogs")
    public BlogResponse createBlogs();

    @GetMapping("category-service/category")
    public List<CategoryResponse> getCategories();
}

