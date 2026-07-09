package com.inventory_management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Integer saleId;


    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;


    @Column(name = "sale_date")
    private LocalDateTime saleDate;

    @PrePersist
    protected void onCreate() {
        if (saleDate == null) {
            saleDate = LocalDateTime.now();
        }
    }


    @ManyToOne
    @JoinColumn(
        name = "customer_id",
        nullable = false
    )
    private Customer customer;


    @ManyToOne
    @JoinColumn(
        name = "user_id",
        nullable = false
    )
    private User user;

    @OneToMany(mappedBy = "sale")
    private List<SaleItem> saleItems;
}