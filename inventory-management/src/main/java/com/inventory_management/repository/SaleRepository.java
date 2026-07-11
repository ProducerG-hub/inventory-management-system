package com.inventory_management.repository;

import com.inventory_management.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("""
    SELECT DISTINCT s
    FROM Sale s
    LEFT JOIN s.saleItems si
    LEFT JOIN si.product p
    WHERE

    LOWER(s.customer.customerName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(str(s.saleDate)) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR 

    LOWER(str(s.saleId)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Sale> searchSales(
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
