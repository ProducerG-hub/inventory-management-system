package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponseDTO {

    private Integer supplierId;

    private String supplierName;

    private String companyName;

    private String phone;

    private String email;

    private String street;

    private String district;

}