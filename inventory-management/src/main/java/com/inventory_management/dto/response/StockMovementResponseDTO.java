package com.inventory_management.dto.response;

import com.inventory_management.entity.enums.movementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponseDTO {

    private Integer movementId;

    private Integer quantity;

    private movementType movementType;

    private LocalDateTime movementDate;

    private String remarks;

    private Integer productId;

    private String productName;

    private Integer userId;

    private String userFullName;

}