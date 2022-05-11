package com.javafsd.project.blogpost.service;

import com.javafsd.project.blogpost.exception.ResourceNotFoundException;
import com.javafsd.project.blogpost.models.Blog;
import com.javafsd.project.blogpost.repository.BlogRepository;
import com.javafsd.project.blogpost.request.AdminBlogRequest;
import com.javafsd.project.blogpost.request.BlogRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BlogService {
    Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    private BlogRepository blogRepository;

    public Blog createBlog(BlogRequest blogRequest) {
            Blog blog = new Blog();
            blog.setTitle(blogRequest.getTitle());
            blog.setContent(blogRequest.getContent());
            blog.setCategory(blogRequest.getCategory());
            blog.setCreatedAt();
            blog.setUserId(blogRequest.getUserId());
            return blogRepository.save(blog);

    }

    public List<Blog> getBlogs(){
        return blogRepository.findAll();
    }

    public ResponseEntity < Blog > getBlogById(String id) throws ResourceNotFoundException {
            Blog blog = blogRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Blog not found for this id :: " + id));
            return ResponseEntity.ok().body(blog);
        }

    public Map<String, Boolean> deleteBlog(String id) throws ResourceNotFoundException
    {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id :: " + id));

        blogRepository.delete(blog);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public ResponseEntity<Blog> updateBlog(BlogRequest blogRequest, String id) throws ResourceNotFoundException {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id :: " + id));

        blog.setTitle(blogRequest.getTitle());
        blog.setContent(blogRequest.getContent());
        blog.setCategory(blogRequest.getCategory());
        blog.setCreatedAt();
        blog.setUserId(blogRequest.getUserId());
        final Blog updatedBlog = blogRepository.save(blog);
        return ResponseEntity.ok(updatedBlog);
    }

    public ResponseEntity<Blog> approveBlog(AdminBlogRequest adminBlogRequest) throws ResourceNotFoundException {
        Blog blog = blogRepository.findById(adminBlogRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found for this id :: " + adminBlogRequest.getId()));
        blog.setApproved(adminBlogRequest.getApproved());
        final Blog updatedBlog = blogRepository.save(blog);
        return ResponseEntity.ok(updatedBlog);
    }
}
