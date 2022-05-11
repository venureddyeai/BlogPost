package com.springjwt.springbootsecurityjwt.controllers;

import com.springjwt.springbootsecurityjwt.Utility.Email;
import com.springjwt.springbootsecurityjwt.Utility.SendMail;
import com.springjwt.springbootsecurityjwt.models.Category;
import com.springjwt.springbootsecurityjwt.models.User;
import com.springjwt.springbootsecurityjwt.payload.request.*;
import com.springjwt.springbootsecurityjwt.repository.UserRepository;
import com.springjwt.springbootsecurityjwt.response.BlogResponse;
import com.springjwt.springbootsecurityjwt.security.services.UserDetailsImpl;
import com.springjwt.springbootsecurityjwt.security.services.UserDetailsServiceImpl;
import com.springjwt.springbootsecurityjwt.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('BLOGGER')")
    public List<BlogResponse> getBlogs(@Valid @RequestBody UserRequest userRequest) {
        List<BlogResponse> blogs=blogService.getBlogs();
        List<BlogResponse> approvedBlogs=new ArrayList<>();

        Optional<User> user = userRepository.findByUsername(userRequest.getUserName());
              //  .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " +userRequest.getUsername()));

        if(user.isPresent()) {
            Set<Category> categories = user.get().getCategory();

            for (BlogResponse blog : blogs) {
                //Only approved blogs should be visible to users
                if (blog.getApproved() != null && blog.getApproved().toLowerCase().equals("true")) {
                    for (Category category : categories) {
                        if (category.getName().equals(blog.getCategory())) {
                            //Only Blogs related to User Interest should be shown
                            approvedBlogs.add(blog);
                        }
                    }
                }
            }
            return approvedBlogs;
        }
        return approvedBlogs;
    }
    @GetMapping("/blogsToApprove")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BlogResponse> getBlogsToApprove(){
        List<BlogResponse> blogs=blogService.getBlogs();
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
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable String id,@Valid @RequestBody UserRequest userRequest){
        User user = userRepository.findByUsername(userRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userRequest.getUserName()));
        Set<Category> categories= user.getCategory();
        ResponseEntity<BlogResponse> blog=blogService.getBlogById(id);
        if(blog.getBody().getApproved()!=null && blog.getBody().getApproved().toLowerCase()!="false"){
            for(Category category:categories) {
                if (category.getName().equals(blog.getBody().getCategory())) {
                    return blog;
                }
            }
        }
        BlogResponse blogResponse=new BlogResponse();
        blogResponse.setTitle("You can't fetch this blog due to either it's not approve or it's not based on your Category Interest.");
        return ResponseEntity.ok(blogResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('BLOGGER')")
    public  BlogResponse createBlog(@RequestBody @Valid BlogUserRequest blogUserRequest)
    {
        User user = userRepository.findByUsername(blogUserRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + blogUserRequest.getUserName()));
        BlogRequest blog=new BlogRequest();
        blog.setTitle(blogUserRequest.getTitle());
        blog.setContent(blogUserRequest.getContent());
        blog.setCategory(blogUserRequest.getCategory());
        blog.setUserId(Integer.parseInt(user.getId().toString()));
        return blogService.createBlog(blog);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BLOGGER') or hasRole('ADMIN')")
    public Map<String, Boolean> getBlogAfterDeleting(@PathVariable String id,@Valid @RequestBody UserRequest userRequest){
       User user = userRepository.findByUsername(userRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userRequest.getUserName()));
        Set<Category> categories= user.getCategory();

        ResponseEntity<BlogResponse> blog=blogService.getBlogById(id);
        if(blog.getBody().getUserId()==user.getId()) {
            return blogService.getBlogAfterDeleting(id);
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted(You can't delete other blogger's post.", Boolean.FALSE);
        return response;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BLOGGER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateBlog(@RequestBody @Valid BlogUserRequest blogUserRequest, @PathVariable String id){
        User user = userRepository.findByUsername(blogUserRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + blogUserRequest.getUserName()));
        Set<Category> categories= user.getCategory();

        ResponseEntity<BlogResponse> blogToValidate=blogService.getBlogById(id);
        if(blogToValidate.getBody().getUserId()==user.getId()) {
            BlogRequest blog=new BlogRequest();
            blog.setTitle(blogUserRequest.getTitle());
            blog.setContent(blogUserRequest.getContent());
            blog.setCategory(blogUserRequest.getCategory());
            blog.setUserId(Integer.parseInt(user.getId().toString()));
            return blogService.updateBlog(blog,id);
        }
        else
        {
            BlogResponse blogResponse=new BlogResponse();
            blogResponse.setTitle("You can't update other blogger's post.");
            return ResponseEntity.ok(blogResponse);
        }

    }

    @PutMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogResponse> approveBlog(@RequestBody AdminBlogRequest adminBlogRequest) throws AddressException, MessagingException, IOException {
        ResponseEntity<BlogResponse> blog= blogService.approveBlog(adminBlogRequest);
        System.out.println("result= "+blog.getBody().getApproved().toLowerCase(Locale.ROOT));
        if(blog.getBody().getApproved().toLowerCase(Locale.ROOT).equals("false")){
            User user=userRepository.getById(Long.parseLong(String.valueOf(blog.getBody().getUserId())));
            if(user==null) System.out.println("user is null");
            else System.out.println("user is not null");
            String emailId=user.getEmail();
            System.out.println(emailId);
            Email eMail=new Email();
            eMail.sendmail(emailId);
        }
        return blog;
    }
}
