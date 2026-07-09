package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Integer productId;

    private String productName;

    private BigDecimal buyingPrice;

    private BigDecimal sellingPrice;

    private Integer quantity;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private Integer categoryId;

    private String categoryName;

    private Integer supplierId;

    private String supplierName;

}