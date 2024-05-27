package com.example.mjlearnapi.category.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class CategoryCreationResponse {
    Long id;
    String name;
    Long parentCategoryId;
}
