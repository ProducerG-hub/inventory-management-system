package com.inventory_management.repository;

import com.inventory_management.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {
    @Query("""
    SELECT sm
    FROM StockMovement sm
    WHERE

    LOWER(sm.product.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(sm.movementType) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(str(sm.movementDate)) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR 

    LOWER(str(sm.quantity)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<StockMovement> searchStockMovements(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}