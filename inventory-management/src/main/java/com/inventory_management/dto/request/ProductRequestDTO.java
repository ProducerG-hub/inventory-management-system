package com.inventory_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

   @NotBlank(message = "Product name is required")
    private String productName;

    @NotNull(message = "Buying price is required")
    @PositiveOrZero(message = "Buying price must be zero or positive")
    private BigDecimal buyingPrice;

    @NotNull(message = "Selling price is required")
    @PositiveOrZero(message = "Selling price must be zero or positive")
    private BigDecimal sellingPrice;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Product status is required")
    private Boolean isActive;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    @NotNull(message = "Supplier is required")
    private Integer supplierId;
}