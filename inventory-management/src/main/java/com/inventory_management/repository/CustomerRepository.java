package com.inventory_management.repository;

import com.inventory_management.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
