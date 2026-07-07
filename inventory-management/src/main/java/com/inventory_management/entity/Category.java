package com.inventory_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer categoryId;

    @Column(name="category_name")
    private String categoryName;

    @Column(name="description")
    private String description;

}

