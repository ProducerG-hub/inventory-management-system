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
public class SaleItemRequestDTO {

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal subtotal;

    private Integer saleId;

    private Integer productId;

}