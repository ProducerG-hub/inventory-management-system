package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponseDTO {

    private Integer saleId;

    private LocalDateTime saleDate;

    private String customerName;

    private String cashier;

    private BigDecimal totalAmount;

    private List<SaleItemResponseDTO> items;

}