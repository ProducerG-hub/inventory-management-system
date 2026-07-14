package com.inventory_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer categoryId;


    @Column(name="category_name", nullable = false)
    private String categoryName;


    @Column(name="description")
    private String description;


    @Column(name="active")
    private Boolean active = true;


    @Column(name="deleted_at")
    private LocalDateTime deletedAt;

}