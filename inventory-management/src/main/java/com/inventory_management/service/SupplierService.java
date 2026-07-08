package com.inventory_management.service;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;

import java.util.List;


public interface SupplierService {


    SupplierResponseDTO createSupplier(SupplierRequestDTO request);


    List<SupplierResponseDTO> getAllSuppliers();


    SupplierResponseDTO getSupplierById(Integer supplierId);


    SupplierResponseDTO updateSupplier(
            Integer supplierId,
            SupplierRequestDTO request
    );


    void deleteSupplier(Integer supplierId);

}