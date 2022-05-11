package com.javafsd.project.blogpost.controllers;

import com.javafsd.project.blogpost.exception.ResourceNotFoundException;
import com.javafsd.project.blogpost.models.Blog;
import com.javafsd.project.blogpost.request.BlogRequest;
import com.javafsd.project.blogpost.service.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogControllerTest {

    @InjectMocks
    BlogController blogController;

    @Mock
    BlogService blogService;

    private BlogRequest blogRequest;
    private Blog blog;
    private List<Blog> blogList;
    private final static String ID = "23";
    private final static String INVALID_ID = "15";
    LocalDateTime presentTime = LocalDateTime.now();

    @BeforeEach
    void init() {
        blogRequest = createNewBlogRequest();
        blog = createNewBlog(blogRequest);
        blogList = createBlogList();
    }

    private List<Blog> createBlogList() {
        List<Blog> blogList = new ArrayList<>();
        blogList.add(createNewBlog(blogRequest));
        return blogList;
    }

    private Blog createNewBlog(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setTitle(this.blogRequest.getTitle());
        blog.setContent(this.blogRequest.getContent());
        blog.setCategory(this.blogRequest.getCategory());
        blog.setCreatedAt(presentTime);
        blog.setUserId(this.blogRequest.getUserId());
        blog.setId("20");
        blog.setApproved("approved");
        return blog;
    }

    private BlogRequest createNewBlogRequest() {
        blogRequest = new BlogRequest();
        blogRequest.setTitle("My First Blog on Travell");
        blogRequest.setCategory("Travell");
        blogRequest.setContent("Exploring New World is Part of My Gene");
        blogRequest.setUserId(23);
        return blogRequest;
    }



    @Test
    void createBlog() {
        when(blogService.createBlog(blogRequest)).thenReturn(blog);
        blog = blogController.createBlog(blogRequest);
        Assertions.assertEquals(blogRequest.getTitle(),blog.getTitle());
    }

    @Test
    void getBlogById() throws ResourceNotFoundException {
        ResponseEntity<Blog> response = ResponseEntity.ok().body(blog);
        when(blogService.getBlogById(ID)).thenReturn(response);
        response = blogController.getBlogById(ID);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getBlogs() {
        when(blogService.getBlogs()).thenReturn(blogList);
        blogList = blogController.getBlogs();
        Assertions.assertTrue(!blogList.isEmpty());
    }

    @Test
    void getBlogAfterDeleting() throws ResourceNotFoundException {
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        when(blogService.deleteBlog(ID)).thenReturn(response);
        response = blogController.getBlogAfterDeleting(ID);
        Assertions.assertTrue(response.get("deleted"));
    }

    @Test
    void updateBlog() throws ResourceNotFoundException {
        ResponseEntity<Blog> response = ResponseEntity.ok().body(blog);
        when(blogService.updateBlog(blogRequest,ID)).thenReturn(response);
        response = blogController.updateBlog(blogRequest,ID);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}