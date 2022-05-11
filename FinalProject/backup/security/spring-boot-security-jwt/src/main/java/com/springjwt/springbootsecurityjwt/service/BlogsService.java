package com.springjwt.springbootsecurityjwt.service;

import com.springjwt.springbootsecurityjwt.feignclient.CommonFeignClient;
import com.springjwt.springbootsecurityjwt.feignclient.CustomFeignClient;
import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogsService {

    Logger logger = LoggerFactory.getLogger(BlogsService.class);

    @Autowired
    CustomFeignClient customFeignClient;

    @CircuitBreaker(name = "blogService",
            fallbackMethod = "fallbackGetBlogs")
    public List<BlogResponse> getBlogs () {
        List<BlogResponse> blogs =
                customFeignClient.getBlogs();

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
        ResponseEntity<BlogResponse> blog=customFeignClient.getBlogById(id);
        return blog;
    }

    public ResponseEntity<BlogResponse> fallbackGetBlogById (String id,Throwable th) {
        logger.error("Error = " + th);
        return ResponseEntity.ok().body(new BlogResponse());
    }
}
