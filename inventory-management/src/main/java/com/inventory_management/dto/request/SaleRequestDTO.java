package com.inventory_management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestDTO {

    private BigDecimal totalAmount;

    private Integer customerId;

    private Integer userId;

}