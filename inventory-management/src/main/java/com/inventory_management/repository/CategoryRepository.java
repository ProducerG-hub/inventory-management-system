package com.inventory_management.repository;

import com.inventory_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
    SELECT c
    FROM Category c
    WHERE

    LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Category> searchCategories(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
