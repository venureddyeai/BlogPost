package com.javafsd.project.blogpost.response;

import com.javafsd.project.blogpost.models.Blog;

import java.time.LocalDateTime;

public class BlogResponse {
    private String id;

    private String title;

    private String content;

    private String category;

    private LocalDateTime createdAt;

    private int UserId;

    public BlogResponse(Blog blog) {
        this.id = blog.getId();
        this.title=blog.getTitle();
        this.content=blog.getContent();
        this.createdAt=blog.getCreatedAt();
        this.category = blog.getCategory();
    }

    public String getBlogId() {
        return id;
    }

    public void setBlogId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
