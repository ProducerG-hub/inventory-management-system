package com.inventory_management.service;

import com.inventory_management.dto.request.SaleRequestDTO;
import com.inventory_management.dto.response.SaleResponseDTO;
import org.springframework.data.domain.Page;

public interface SaleService {

    SaleResponseDTO createSale(SaleRequestDTO request);

        Page<SaleResponseDTO> getAllSales(
            int page,
            int size,
            String sortBy,
            String sortDir
        );

        Page<SaleResponseDTO> searchSales(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
        );

    SaleResponseDTO getSaleById(Integer saleId);

    SaleResponseDTO updateSale(Integer saleId, SaleRequestDTO request);

    void deleteSale(Integer saleId);

}