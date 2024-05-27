package com.example.mjlearnapi.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    @Query("SELECT ce FROM CategoryEntity ce WHERE (ce.parentCategoryId IS NULL)")
    List<CategoryEntity> findTopCategories();

    @Query("SELECT ce FROM CategoryEntity ce WHERE (ce.parentCategoryId = :categoryId)")
    List<CategoryEntity> findChildrenForCategory(@Param("categoryId") Long categoryId);
}
