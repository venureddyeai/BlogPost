package com.javafsd.project.blogpost.service;

import com.javafsd.project.blogpost.exception.ResourceNotFoundException;
import com.javafsd.project.blogpost.models.Blog;
import com.javafsd.project.blogpost.repository.BlogRepository;
import com.javafsd.project.blogpost.request.BlogRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

	 @Mock
	    BlogRepository blogRepository;

	    @InjectMocks
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
	    void testCreateBlog() {
	        when(blogRepository.save(Mockito.any(Blog.class))).thenReturn(blog);
	        blog = blogService.createBlog(blogRequest);
	        verify(blogRepository, times(1)).save(Mockito.any(Blog.class));
	        assertEquals(blog.getUserId(), blogRequest.getUserId());
	    }

	    @Test
	    void testGetBlogs() {
	        when(blogRepository.findAll()).thenReturn(blogList);
	        blogList = blogService.getBlogs();
	        assertTrue(!blogList.isEmpty());
	    }

	    @Test
	    void testGetBlogById() throws ResourceNotFoundException {
	        when(blogRepository.findById(Mockito.any(String.class))).thenReturn(Optional.ofNullable(blog));
	        ResponseEntity<Blog> response = blogService.getBlogById(ID);
	        assertNotNull(response.getBody());
	    }

	    @Test
	    void testGetBlogById_Exception() {
	        when(blogRepository.findById(Mockito.any(String.class))).thenThrow(RuntimeException.class);
	        Assertions.assertThrows(RuntimeException.class, () -> blogService.getBlogById(INVALID_ID));
	    }

	    @Test
	    void testDeleteBlog() throws ResourceNotFoundException {
	        when(blogRepository.findById(Mockito.any(String.class))).thenReturn(Optional.ofNullable(blog));
	        Map<String, Boolean> responseMap = blogService.deleteBlog(ID);
	        assertTrue(responseMap.get("deleted"));
	    }

	    @Test
	    void testUpdateBlog() throws ResourceNotFoundException {
	        when(blogRepository.findById(ID)).thenReturn(Optional.ofNullable(blog));
	        ResponseEntity<Blog> response = blogService.updateBlog(blogRequest, ID);
	        verify(blogRepository, times(1)).save(Mockito.any(Blog.class));
	        assertTrue(response.getStatusCode().is2xxSuccessful());

	    }


}
