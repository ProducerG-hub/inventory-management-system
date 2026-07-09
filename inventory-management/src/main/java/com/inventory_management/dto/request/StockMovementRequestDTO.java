package com.inventory_management.dto.request;

import com.inventory_management.entity.enums.movementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequestDTO {

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "Movement type is required")
    private movementType movementType;

    private String remarks;

    @NotNull(message = "Product is required")
    private Integer productId;

    @NotNull(message = "User is required")
    private Integer userId;

}