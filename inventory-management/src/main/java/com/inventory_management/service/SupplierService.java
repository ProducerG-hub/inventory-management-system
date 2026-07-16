package com.inventory_management.service;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import org.springframework.data.domain.Page;

public interface SupplierService {

    SupplierResponseDTO createSupplier(
            SupplierRequestDTO request
    );

    Page<SupplierResponseDTO> getAllSuppliers(
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<SupplierResponseDTO> searchSuppliers(
            String keyword,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    SupplierResponseDTO getSupplierById(
            Integer supplierId
    );

    SupplierResponseDTO updateSupplier(
            Integer supplierId,
            SupplierRequestDTO request
    );

    void deleteSupplier(
            Integer supplierId
    );

    void restoreSupplier(
            Integer supplierId
    );

}