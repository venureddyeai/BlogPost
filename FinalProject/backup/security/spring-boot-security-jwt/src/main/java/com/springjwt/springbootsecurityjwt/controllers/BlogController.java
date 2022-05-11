package com.springjwt.springbootsecurityjwt.controllers;

import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import com.springjwt.springbootsecurityjwt.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    BlogsService blogsService;

    @GetMapping
    @PreAuthorize("hasRole('BLOGGER')  or hasRole('ADMIN')")
    public List<BlogResponse> getBlogs(){
        List<BlogResponse> blogs=blogsService.getBlogs();
        List<BlogResponse> approvedBlogs=new ArrayList<>();
        for (BlogResponse blog : blogs) {
            if (blog.getApproved()!=null) {
                approvedBlogs.add(blog);
            }
        }
        return approvedBlogs;
    }
    @GetMapping("/blogsToApprove")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BlogResponse> getBlogsToApprove(){
        List<BlogResponse> blogs=blogsService.getBlogs();
        List<BlogResponse> approvedBlogs=new ArrayList<>();
        for (BlogResponse blog : blogs) {
            if (blog.getApproved()==null) {
                approvedBlogs.add(blog);
            }
        }
        return approvedBlogs;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('BLOGGER')  or hasRole('ADMIN')")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable String id){
        return blogsService.getBlogById(id);
    }


}
