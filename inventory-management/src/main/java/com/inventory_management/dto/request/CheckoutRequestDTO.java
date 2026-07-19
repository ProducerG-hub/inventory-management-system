package com.inventory_management.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequestDTO {

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @Valid
    @NotEmpty(message = "Cart cannot be empty")
    private List<CheckoutItemDTO> items;

}