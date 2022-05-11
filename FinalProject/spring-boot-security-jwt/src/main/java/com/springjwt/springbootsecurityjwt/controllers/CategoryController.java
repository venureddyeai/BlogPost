package com.springjwt.springbootsecurityjwt.controllers;


import com.springjwt.springbootsecurityjwt.models.Category;
import com.springjwt.springbootsecurityjwt.models.User;
import com.springjwt.springbootsecurityjwt.payload.request.CategoryRequest;
import com.springjwt.springbootsecurityjwt.payload.request.InterestRequest;
import com.springjwt.springbootsecurityjwt.payload.response.MessageResponse;
import com.springjwt.springbootsecurityjwt.repository.CategoryRepository;
import com.springjwt.springbootsecurityjwt.repository.UserRepository;
import com.springjwt.springbootsecurityjwt.response.CategoryResponse;
import com.springjwt.springbootsecurityjwt.security.services.UserDetailsImpl;
import com.springjwt.springbootsecurityjwt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('BLOGGER')  or hasRole('ADMIN')")
    public List<CategoryResponse> getCategories(){
       return categoryService.getCategories();
    }

    @PostMapping("/userInterest")
    @PreAuthorize("hasRole('BLOGGER')")
    public ResponseEntity<?> saveUserInterest(@RequestBody InterestRequest interestRequest) {
        List<CategoryResponse> categoriesFromMicroservice = categoryService.getCategories();
        User user = userRepository.findByUsername(interestRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + interestRequest.getUserName()));

        System.out.println("User name"+ user.getUsername());
        System.out.println("User Category"+ user.getCategory());
        Set<Category> userInterest = new HashSet<>();



        for (String categoryName : interestRequest.getCategories()) {
            boolean found = categoriesFromMicroservice.stream()
                    .anyMatch(p -> p.getName().equals(categoryName));
            System.out.println("Category Found in Microservice"+ found);
            if (found) {
                Category category=new Category(categoryName);
                Optional<Category> categoryFromDB=categoryService.getCategoryFromDB(categoryName);
                if(categoryFromDB.isPresent())
                {
                    System.out.println("Category From SQL DB is present"+ Boolean.TRUE);
                    //userInterest.add(category);
                    userInterest.add(categoryFromDB.get());
                }
                else {
                    System.out.println("Category From SQL DB is present"+ Boolean.FALSE);
                    categoryService.saveCategories(category);
                    userInterest.add(category);
                }
            } else
                return ResponseEntity.ok(new MessageResponse(categoryName + " does not exists."));

            System.out.println("Save User Interest End");
        }
        user.setCategory(userInterest);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Interest saved successfully!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> getCategoryById (@PathVariable String id)
    {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Boolean> getCategoryAfterDeleting(@PathVariable String id){
        return categoryService.getCategoryAfterDeleting(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody @Valid CategoryRequest category, @PathVariable String id) {
        return categoryService.updateCategory(category,id);
    }

}
