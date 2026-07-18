package com.inventory_management.repository;

import com.inventory_management.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Page<User> findByIsActive(
            Boolean isActive,
            Pageable pageable
    );

    @Query("""
        SELECT u
        FROM User u
        WHERE
            u.isActive = :active
        AND
        (
            LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            OR
            LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    Page<User> searchUsers(
            @Param("keyword") String keyword,
            @Param("active") Boolean active,
            Pageable pageable
    );

}