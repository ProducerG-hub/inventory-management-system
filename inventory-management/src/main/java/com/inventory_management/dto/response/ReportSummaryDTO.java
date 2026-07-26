package com.inventory_management.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportSummaryDTO {

    private Long totalSales;

    private BigDecimal totalRevenue;

    private BigDecimal totalProfit;

    private Long lowStockProducts;

}