package com.inventory_management.repository;

import com.inventory_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("""
    SELECT u
    FROM User u
    WHERE
    LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR
    LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
    OR
    LOWER(u.role) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);

}