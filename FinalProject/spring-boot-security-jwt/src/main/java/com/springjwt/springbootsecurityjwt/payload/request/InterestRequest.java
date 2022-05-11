package com.springjwt.springbootsecurityjwt.payload.request;

import java.util.List;

public class InterestRequest {

    private String userName;

    private List<String> categories;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
