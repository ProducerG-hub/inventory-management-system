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
public class DashboardRecentProductDTO {


    private Integer productId;

    private String productName;

    private String categoryName;

    private Integer quantity;

    private BigDecimal sellingPrice;

    private LocalDateTime createdAt;


}