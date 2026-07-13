package com.inventory_management.repository;


import com.inventory_management.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Query("""
    SELECT p
    FROM Product p
    WHERE

    LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(p.supplier.supplierName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    """)
    Page<Product> searchProducts(
            @Param("keyword") String keyword,
            Pageable pageable
    );



    Long countByQuantityLessThanEqual(Integer quantity);



    List<Product> findTop5ByOrderByCreatedAtDesc();



    List<Product> findTop5ByQuantityLessThanEqualOrderByQuantityAsc(
            Integer quantity
    );


}