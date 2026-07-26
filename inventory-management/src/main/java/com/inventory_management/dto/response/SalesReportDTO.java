package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesReportDTO {

    private Integer saleId;

    private String customerName;

    private String cashierName;

    private Integer totalItems;

    private BigDecimal totalAmount;

    private LocalDateTime saleDate;

}