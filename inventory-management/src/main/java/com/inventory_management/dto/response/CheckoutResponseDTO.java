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
public class CheckoutResponseDTO {

    private Integer saleId;

    private String customerName;

    private String cashierName;

    private LocalDateTime saleDate;

    private BigDecimal totalAmount;

    private List<ReceiptItemDTO> items;

}