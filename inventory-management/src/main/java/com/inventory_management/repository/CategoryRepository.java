package com.inventory_management.repository;

import com.inventory_management.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {


    // Get categories by status (Active / Inactive)
    Page<Category> findByActive(
            Boolean active,
            Pageable pageable
    );


    // Search categories by keyword + status
    @Query("""
        SELECT c
        FROM Category c
        WHERE c.active = :active
        AND
        (
            LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<Category> searchCategories(
            @Param("keyword") String keyword,
            @Param("active") Boolean active,
            Pageable pageable
    );


    // Prevent duplicate active categories
    boolean existsByCategoryNameIgnoreCaseAndActiveTrue(
            String categoryName
    );

}