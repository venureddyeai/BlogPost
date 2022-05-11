package com.javafsd.project.blogpost.controllers;

import com.javafsd.project.blogpost.exception.ResourceNotFoundException;
import com.javafsd.project.blogpost.models.Blog;

import com.javafsd.project.blogpost.request.AdminBlogRequest;
import com.javafsd.project.blogpost.request.BlogRequest;
import com.javafsd.project.blogpost.response.BlogResponse;
import com.javafsd.project.blogpost.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    /*This method should save blog and return savedBlog Object */
    @PostMapping
    public  Blog createBlog(@RequestBody @Valid BlogRequest blogRequest)
    {
        return blogService.createBlog(blogRequest);
    }

    /*This method should fetch the blog taking its id and return the respective blog */
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById (@PathVariable String id) throws ResourceNotFoundException {
        return blogService.getBlogById(id);
    }

    /*This method should fetch all blogs */
    @GetMapping
    public List<Blog> getBlogs(){
        List<Blog> blogs=blogService.getBlogs();
        return blogs;
    }

    /*This method should delete the blog taking its id and return the deleted blog */
    @DeleteMapping("/{id}")
    public Map<String, Boolean> getBlogAfterDeleting(@PathVariable String id) throws ResourceNotFoundException {
        return blogService.deleteBlog(id);
    }

    /*This method should update blog and return the updatedBlog */
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@RequestBody @Valid BlogRequest blog, @PathVariable String id) throws ResourceNotFoundException{
        return blogService.updateBlog(blog,id);
    }

    /*This method should use by admin to approve blog and return the updatedBlog */
    @PutMapping("/approve")
    public ResponseEntity<Blog> approveBlog(@RequestBody AdminBlogRequest adminBlogRequest) throws ResourceNotFoundException {
        return blogService.approveBlog(adminBlogRequest);
    }
}
