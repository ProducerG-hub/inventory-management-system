package com.inventory_management.repository;

import com.inventory_management.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    @Query("""
    SELECT s
    FROM Supplier s
    WHERE

    LOWER(s.supplierName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(s.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(str(s.phone)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Supplier> searchSuppliers(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}