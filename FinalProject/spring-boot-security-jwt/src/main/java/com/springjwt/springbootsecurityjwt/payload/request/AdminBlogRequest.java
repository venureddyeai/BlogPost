package com.springjwt.springbootsecurityjwt.payload.request;

public class AdminBlogRequest {
    private String id;

    private String approved;

    public AdminBlogRequest()
    {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }
}
