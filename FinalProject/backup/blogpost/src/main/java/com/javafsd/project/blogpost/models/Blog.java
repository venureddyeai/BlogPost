package com.javafsd.project.blogpost.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;


@Document
public class Blog {

    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private String title;

    private String content;

    private String approved;

    private String category;

    private LocalDateTime createdAt=LocalDateTime.now();

    private int UserId;

    public Blog() {
        super();
    }

    public Blog(String id, String title, String content, String approved, String category, LocalDateTime createdAt, int userId) {
        id = id;
        this.title = title;
        this.content = content;
        this.approved = approved;
        this.category = category;
        this.createdAt = LocalDateTime.now();
        UserId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
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

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "Id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", approved='" + approved + '\'' +
                ", category='" + category + '\'' +
                ", CreatedAt=" + createdAt +
                ", UserId=" + UserId +
                '}';
    }
}