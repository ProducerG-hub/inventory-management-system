package com.inventory_management.entity;

import com.inventory_management.entity.enums.movementType;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Integer movementId;


    @Column(name = "quantity", nullable = false)
    private Integer quantity;


    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private movementType movementType;


    @Column(name = "movement_date")
    private LocalDateTime movementDate;


    @Column(name = "remarks")
    private String remarks;


    @ManyToOne
    @JoinColumn(
        name = "product_id",
        nullable = false
    )
    private Product product;


    @ManyToOne
    @JoinColumn(
        name = "user_id",
        nullable = false
    )
    private User user;
}