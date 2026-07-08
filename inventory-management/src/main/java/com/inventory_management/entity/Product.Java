package com.inventory_management.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "product_id")
private Integer productId;

@Column(name = "product_name", nullable = false)
private String productName;

@Column(name = "buying_price", nullable = false)
private BigDecimal buyingPrice;

@Column(name = "selling_price", nullable = false)
private BigDecimal sellingPrice;

@Column(nullable = false)
private Integer quantity;

@Column(name = "is_active")
private Boolean isActive;

@Column(name = "created_at")
private LocalDateTime createdAt;

//Relationship with Category
@ManyToOne
@JoinColumn(name = "category_id")
private Category category;

//Relationship with Supplier
@ManyToOne
@JoinColumn(name = "supplier_id")
private Supplier supplier;

@OneToMany(mappedBy = "product")
private List<SaleItem> saleItems;
}