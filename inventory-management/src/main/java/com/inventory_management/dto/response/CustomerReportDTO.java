package com.inventory_management.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerReportDTO {

    private Integer customerId;

    private String customerName;

    private String phone;

    private Long totalOrders;

    private BigDecimal totalSpent;

    private LocalDateTime lastPurchaseDate;

}