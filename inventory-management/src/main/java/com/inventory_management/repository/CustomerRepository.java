package com.inventory_management.repository;

import com.inventory_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.inventory_management.dto.response.CustomerReportDTO;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("""
    SELECT c
    FROM Customer c
    WHERE

    LOWER(c.customerName) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(c.email) LIKE LOWER(CONCAT('%', :keyword, '%'))

    OR

    LOWER(str(c.phone)) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<Customer> searchCustomers(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("""
    SELECT new com.inventory_management.dto.response.CustomerReportDTO(
    
        c.customerId,
    
        c.customerName,
    
        c.phone,
    
        COUNT(s),
    
        COALESCE(SUM(s.totalAmount),0),
    
        MAX(s.saleDate)
    
    )
    
    FROM Customer c
    
    LEFT JOIN c.sales s
    
    GROUP BY
        c.customerId,
        c.customerName,
        c.phone
    
    ORDER BY SUM(s.totalAmount) DESC
    
    """)
        List<CustomerReportDTO> getCustomerReport();
}
