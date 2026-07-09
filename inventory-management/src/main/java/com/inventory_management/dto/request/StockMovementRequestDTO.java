package com.inventory_management.dto.request;

import com.inventory_management.entity.enums.movementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequestDTO {

    private Integer quantity;

    private movementType movementType;

    private String remarks;

    private Integer productId;

    private Integer userId;

}