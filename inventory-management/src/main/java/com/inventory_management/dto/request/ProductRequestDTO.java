package com.inventory_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    private String productName;

    private BigDecimal buyingPrice;

    private BigDecimal sellingPrice;

    private Integer quantity;

    private Boolean isActive;

    private Integer categoryId;

    private Integer supplierId;

}