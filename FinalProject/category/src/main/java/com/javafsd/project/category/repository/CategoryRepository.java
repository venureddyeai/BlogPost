package com.javafsd.project.category.repository;

import com.javafsd.project.category.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    Boolean existsByName(String name);
}
