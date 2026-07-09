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
public class SaleResponseDTO {

    private Integer saleId;

    private BigDecimal totalAmount;

    private LocalDateTime saleDate;

    private Integer customerId;

    private String customerName;

    private Integer userId;

    private String userFullName;

}