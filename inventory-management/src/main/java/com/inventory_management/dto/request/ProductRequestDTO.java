package com.inventory_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "ProductRequestDTO", description = "Data Transfer Object for Product requests")
public class ProductRequestDTO {

    @Schema(description = "The name of the product", example = "Laptop")
    @NotBlank(message = "Product name is required")
    private String productName;

    @Schema(description = "The buying price of the product", example = "1000.00")
    @NotNull(message = "Buying price is required")
    @PositiveOrZero(message = "Buying price must be zero or positive")
    private BigDecimal buyingPrice;

    @Schema(description = "The selling price of the product", example = "1500.00")
    @NotNull(message = "Selling price is required")
    @PositiveOrZero(message = "Selling price must be zero or positive")
    private BigDecimal sellingPrice;

    @Schema(description = "The quantity of the product", example = "10")
    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Integer quantity;

    @Schema(description = "The status of the product", example = "true")
    private Boolean isActive;

    @Schema(description = "The category ID of the product", example = "1")
    @NotNull(message = "Category is required")
    private Integer categoryId;

    @Schema(description = "The supplier ID of the product", example = "1")
    @NotNull(message = "Supplier is required")
    private Integer supplierId;

    @Schema(description = "The ID of the user who created the product", example = "1")
    @NotNull(message = "User ID is required")
    private Integer userId;
}