package com.inventory_management.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesTrendDTO {

    private LocalDate date;

    private Long totalOrders;

    private BigDecimal totalRevenue;

}