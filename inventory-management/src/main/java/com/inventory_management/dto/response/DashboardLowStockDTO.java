package com.inventory_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardLowStockDTO {


    private Integer productId;

    private String productName;

    private Integer quantity;

    private String categoryName;


}