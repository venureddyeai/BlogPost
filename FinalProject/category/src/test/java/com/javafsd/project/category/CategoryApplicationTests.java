package com.javafsd.project.category;

import com.javafsd.project.category.exception.ResourceNotFoundException;
import com.javafsd.project.category.model.Category;
import com.javafsd.project.category.repository.CategoryRepository;
import com.javafsd.project.category.request.CategoryRequest;
import com.javafsd.project.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


class CategoryApplicationTests {

}
