package com.inventory_management.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReportDTO {

    private Integer productId;

    private String productName;

    private String categoryName;

    private String supplierName;

    private BigDecimal buyingPrice;

    private BigDecimal sellingPrice;

    private Integer quantity;

    private BigDecimal stockValue;

    private String stockStatus;

}