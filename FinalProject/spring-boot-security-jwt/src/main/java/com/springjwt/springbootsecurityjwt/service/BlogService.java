package com.springjwt.springbootsecurityjwt.service;

import com.springjwt.springbootsecurityjwt.feignclients.BlogFeignClient;
import com.springjwt.springbootsecurityjwt.payload.request.AdminBlogRequest;
import com.springjwt.springbootsecurityjwt.payload.request.BlogRequest;
import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogService {
    Logger logger = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    BlogFeignClient feignClient;

    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackGetBlogs")
    public List<BlogResponse> getBlogs () {
        List<BlogResponse> blogs =
                feignClient.getBlogs();

        return blogs;
    }

    public List<BlogResponse> fallbackGetBlogs (Throwable th) {
        logger.error("Error = " + th);
        List<BlogResponse> blogs=new ArrayList<BlogResponse>();
        return blogs;
    }

    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackGetBlogById")
    public ResponseEntity<BlogResponse> getBlogById (String id) {
        ResponseEntity<BlogResponse> blog=feignClient.getBlogById(id);
        return blog;
    }

    public ResponseEntity<BlogResponse> fallbackGetBlogById (String id,Throwable th) {
        logger.error("Error = " + th);
        return ResponseEntity.ok().body(new BlogResponse());
    }

    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackCreateBlog")
    public BlogResponse createBlog (BlogRequest blogRequest) {
        BlogResponse blog=feignClient.createBlog(blogRequest);
        return blog;
    }

    public BlogResponse fallbackCreateBlog (BlogRequest blogRequest, Throwable th) {
        logger.error("Error = " + th);
        return new BlogResponse();
    }

    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackGetBlogAfterDeleting")
    public Map<String, Boolean> getBlogAfterDeleting (String id) {
        feignClient.getBlogAfterDeleting(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Map<String, Boolean> fallbackGetBlogAfterDeleting (String id,Throwable th) {
        logger.error("Error = " + th);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.FALSE);
        return response;
    }
    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackUpdateBlog")
    public ResponseEntity<BlogResponse> updateBlog (BlogRequest blogRequest, String id) {
        ResponseEntity<BlogResponse> blog=feignClient.updateBlog(blogRequest,id);
        return blog;
    }

    public ResponseEntity<BlogResponse> fallbackUpdateBlog (BlogRequest blogRequest, String id,Throwable th) {
        logger.error("Error = " + th);
        return ResponseEntity.ok().body(new BlogResponse());
    }

    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackApproveBlog")
    public ResponseEntity<BlogResponse> approveBlog(AdminBlogRequest adminBlogRequest){
        ResponseEntity<BlogResponse> blog=feignClient.approveBlog(adminBlogRequest);
        return blog;
    }

    public ResponseEntity<BlogResponse> fallbackApproveBlog (AdminBlogRequest adminBlogRequest,Throwable th) {
        logger.error("Error = " + th);
        return ResponseEntity.ok().body(new BlogResponse());
    }
}
