package com.javafsd.project.blogpost.repository;

import com.javafsd.project.blogpost.models.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends MongoRepository<Blog,String> {
}
