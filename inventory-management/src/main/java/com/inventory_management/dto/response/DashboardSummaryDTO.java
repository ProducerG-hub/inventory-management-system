package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {

    private Long totalProducts;

    private Long totalCategories;

    private Long totalSuppliers;

    private Long totalUsers;

    private Long lowStockProducts;

}