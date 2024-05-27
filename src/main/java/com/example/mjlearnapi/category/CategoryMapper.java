package com.example.mjlearnapi.category;

import com.example.mjlearnapi.category.payload.response.CategoryCreationResponse;
import com.example.mjlearnapi.category.payload.response.CategoryPayload;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
    public static CategoryCreationResponse mapToCategoryCreationResponse(CategoryEntity category) {
        return CategoryCreationResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .parentCategoryId(category.getParentCategoryId())
                .build();
    }

    public static CategoryPayload mapToCategoryPayload(CategoryEntity category) {
        return CategoryPayload.builder()
                .id(category.getId())
                .name(category.getName())
                .parentCategoryId(category.getParentCategoryId())
                .build();
    }

    public static List<CategoryPayload> mapToCategoryPayloadList(List<CategoryEntity> categories) {
        return categories.stream().map(CategoryMapper::mapToCategoryPayload).collect(Collectors.toList());
    }
}
