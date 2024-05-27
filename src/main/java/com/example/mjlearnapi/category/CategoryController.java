package com.example.mjlearnapi.category;

import com.example.mjlearnapi.category.payload.request.CategoryCreationRequest;
import com.example.mjlearnapi.category.payload.response.CategoryCreationResponse;
import com.example.mjlearnapi.category.payload.response.CategoryPayload;
import com.example.mjlearnapi.infrastructure.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CategoryController {
    CategoryRepository categoryRepository;
    @PostMapping
    public ResponseEntity<CategoryCreationResponse> createCategory(@RequestBody @Valid CategoryCreationRequest request) {
        throwIfParentCategoryDoesNotExists(request);
        CategoryEntity savedCategory = categoryRepository.save(CategoryEntity.builder()
                .name(request.getName())
                .parentCategoryId(request.getParentCategoryId())
                .build());
        return ResponseEntity.status(CREATED).body(CategoryMapper.mapToCategoryCreationResponse(savedCategory));
    }

    @GetMapping("/top")
    public ResponseEntity<List<CategoryPayload>> getTopCategories() {
        return ResponseEntity.ok(CategoryMapper.mapToCategoryPayloadList(categoryRepository.findTopCategories()));
    }

    @GetMapping("/children/{categoryId}")
    public ResponseEntity<List<CategoryPayload>> getChildrenForCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(CategoryMapper.mapToCategoryPayloadList(categoryRepository.findChildrenForCategory(categoryId)));
    }

    private void throwIfParentCategoryDoesNotExists(CategoryCreationRequest request) {
        if (request.getParentCategoryId() != null) {
            categoryRepository.findById(request.getParentCategoryId()).orElseThrow(EntityNotFoundException::new);
        }
    }
}
