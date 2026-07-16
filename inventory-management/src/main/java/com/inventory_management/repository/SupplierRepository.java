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

    Page<Supplier> findByActive(
            Boolean active,
            Pageable pageable
    );

    @Query("""
    SELECT s
    FROM Supplier s
    WHERE
        s.active = :active
        AND
        (
            LOWER(s.supplierName) LIKE LOWER(CONCAT('%', :keyword, '%'))

            OR

            LOWER(COALESCE(s.companyName,'')) LIKE LOWER(CONCAT('%', :keyword, '%'))

            OR

            LOWER(COALESCE(s.email,'')) LIKE LOWER(CONCAT('%', :keyword, '%'))

            OR

            LOWER(COALESCE(s.phone,'')) LIKE LOWER(CONCAT('%', :keyword, '%'))

            OR

            LOWER(COALESCE(s.street,'')) LIKE LOWER(CONCAT('%', :keyword, '%'))

            OR

            LOWER(COALESCE(s.district,'')) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<Supplier> searchSuppliers(

            @Param("keyword") String keyword,

            @Param("active") Boolean active,

            Pageable pageable

    );

}