package com.example.mjlearnapi.category.payload.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoryCreationRequest {
    Long id;
    @NotBlank
    String name;
    Long parentCategoryId;
}
