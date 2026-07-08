package com.inventory_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saleitem_id")
    private Integer saleItemId;


    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;


    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;


    @ManyToOne
    @JoinColumn(
        name = "sale_id",
        nullable = false
    )
    private Sale sale;


    @ManyToOne
    @JoinColumn(
        name = "product_id",
        nullable = false
    )
    private Product product;
}