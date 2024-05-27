package com.example.mjlearnapi.category;


import com.example.mjlearnapi.category.payload.request.CategoryCreationRequest;
import com.example.mjlearnapi.category.payload.response.CategoryCreationResponse;
import com.example.mjlearnapi.category.payload.response.CategoryPayload;
import com.example.mjlearnapi.config.SecurityConfig;
import com.example.mjlearnapi.infrastructure.EntityNotFoundException;
import com.example.mjlearnapi.infrastructure.GlobalExceptionHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
@FieldDefaults(level = AccessLevel.PRIVATE)
class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CategoryRepository categoryRepository;
    CategoryEntity parentCategory;
    CategoryEntity childCategory;
    @BeforeEach
    void setUp() {
        parentCategory = CategoryEntity.builder()
                .id(1L)
                .name("Parent")
                .parentCategoryId(null)
                .build();
        childCategory = CategoryEntity.builder()
                .id(2L)
                .name("Child")
                .parentCategoryId(1L)
                .build();

        given(categoryRepository.findById(1L)).willReturn(Optional.of(parentCategory));
        given(categoryRepository.findById(2L)).willReturn(Optional.empty());
        given(categoryRepository.save(ArgumentMatchers.any(CategoryEntity.class))).willReturn(childCategory);
        given(categoryRepository.findTopCategories()).willReturn(Collections.singletonList(parentCategory));
        given(categoryRepository.findChildrenForCategory(1L)).willReturn(Collections.singletonList(childCategory));
    }

    @Test
    void createCategory_ShouldReturnCreatedStatus() throws Exception {
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Child\",\"parentCategoryId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Child"));
    }

    @Test
    void createCategory_ShouldThrowEntityNotFoundException() throws Exception {
        mockMvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Child\",\"parentCategoryId\":2}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTopCategories_ShouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/api/category/top"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Parent"));
    }

    @Test
    void getChildrenForCategory_ShouldReturnOkStatus() throws Exception {
        mockMvc.perform(get("/api/category/children/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("Child"));
    }
}