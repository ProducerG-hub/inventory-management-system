package com.inventory_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {


    private DashboardSummaryDTO summary;


    private List<DashboardRecentProductDTO> recentProducts;


    private List<DashboardLowStockDTO> lowStockProducts;


}