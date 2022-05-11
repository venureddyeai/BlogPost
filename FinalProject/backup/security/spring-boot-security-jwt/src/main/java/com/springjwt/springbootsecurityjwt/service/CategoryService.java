package com.springjwt.springbootsecurityjwt.service;

import com.springjwt.springbootsecurityjwt.feignclient.CommonFeignClient;
import com.springjwt.springbootsecurityjwt.feignclient.CustomFeignClient;
import com.springjwt.springbootsecurityjwt.response.CategoryResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    CustomFeignClient customFeignClient;

    @CircuitBreaker(name = "categoryService",
            fallbackMethod = "fallbackGetCategories")
    public List<CategoryResponse> getCategories () {
        List<CategoryResponse> categories =customFeignClient.getCategories();

        return categories;
    }

    public List<CategoryResponse> fallbackGetCategories (Throwable th) {
        logger.error("Error = " + th);
        List<CategoryResponse> categories=new ArrayList<CategoryResponse>();
        return categories;
    }
}
