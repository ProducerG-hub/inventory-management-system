package com.inventory_management.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopSellingProductDTO {

    private Integer productId;

    private String productName;

    private Long totalQuantitySold;

    private BigDecimal totalRevenue;

    private Long totalOrders;

}