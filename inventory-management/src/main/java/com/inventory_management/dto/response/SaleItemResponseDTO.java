package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemResponseDTO {

    private Integer saleItemId;

    private BigDecimal unitPrice;

    private Integer quantity;

    private BigDecimal subtotal;

    private Integer saleId;

    private Integer productId;

    private String productName;

    private String customerName;

}