package com.inventory_management.repository;

import com.inventory_management.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.inventory_management.dto.response.SalesReportDTO;
import java.util.List;

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

    @Query("""
    SELECT COUNT(s)
    FROM Sale s
    """)
        Long getTotalSales();

        @Query("""
    SELECT COALESCE(SUM(s.totalAmount),0)
    FROM Sale s
    """)
        BigDecimal getTotalRevenue();

    @Query("""
    SELECT new com.inventory_management.dto.response.SalesReportDTO(
    
        s.saleId,
    
        s.customer.customerName,
    
        s.user.fullName,
    
        SIZE(s.saleItems),
    
        s.totalAmount,
    
        s.saleDate
    
    )
    
    FROM Sale s
    
    ORDER BY s.saleDate DESC
    """)
    List<SalesReportDTO> getSalesReport();

    @Query(
            value = """
        SELECT 
            DATE(sale_date) AS sale_date,
            COUNT(*) AS total_orders,
            SUM(total_amount) AS total_revenue

        FROM sales

        GROUP BY DATE(sale_date)

        ORDER BY DATE(sale_date)

        """,
            nativeQuery = true
    )
    List<Object[]> getSalesTrend();

    @Query("""
    SELECT s
    FROM Sale s
    WHERE s.saleDate >= :startDate
    AND s.saleDate < :endDate
    ORDER BY s.saleDate DESC
    """)
    List<Sale> findSalesBetweenDates(

            @Param("startDate")
            LocalDateTime startDate,


            @Param("endDate")
            LocalDateTime endDate

    );
}
