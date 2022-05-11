package com.springjwt.springbootsecurityjwt.feignclients;

import com.springjwt.springbootsecurityjwt.payload.request.AdminBlogRequest;
import com.springjwt.springbootsecurityjwt.payload.request.BlogRequest;
import com.springjwt.springbootsecurityjwt.payload.request.CategoryRequest;
import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import com.springjwt.springbootsecurityjwt.response.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient(value = "api-gateway")
public interface BlogFeignClient {
    @GetMapping("/blog-service/blogs")
    public List<BlogResponse> getBlogs();

    @GetMapping("/blog-service/blogs/{id}")
    public ResponseEntity<BlogResponse> getBlogById( @PathVariable String id);

    @PostMapping("/blog-service/blogs")
    public BlogResponse createBlog(@RequestBody @Valid BlogRequest blogRequest);

    @DeleteMapping("/blog-service/blogs/{id}")
    public Map<String, Boolean> getBlogAfterDeleting(@PathVariable String id);

    @PutMapping("/blog-service/blogs/{id}")
    public ResponseEntity<BlogResponse> updateBlog(@RequestBody @Valid BlogRequest blog, @PathVariable String id);

    @PutMapping("/blog-service/blogs/approve")
    public ResponseEntity<BlogResponse> approveBlog(@RequestBody AdminBlogRequest adminBlogRequest) ;

    @GetMapping("/category-service/category")
    public List<CategoryResponse> getCategories();

    @GetMapping("/category-service/category/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById (@PathVariable String id);

    @PostMapping("/category-service/category")
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest);

    @DeleteMapping("/category-service/category/{id}")
    public Map<String, Boolean> getCategoryAfterDeleting(@PathVariable String id);

    @PutMapping("/category-service/category/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid CategoryRequest category, @PathVariable String id) ;

    }
