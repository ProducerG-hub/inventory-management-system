package com.inventory_management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequestDTO {

    @NotBlank(message = "Supplier name is required")
    private String supplierName;

    private String companyName;

    private String phone;

    @Email(message = "Email should be valid")
    private String email;

    private String street;

    private String district;

}