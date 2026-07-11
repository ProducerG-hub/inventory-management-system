package com.inventory_management.service;

import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.response.SaleItemResponseDTO;
import org.springframework.data.domain.Page;

public interface SaleItemService {

    SaleItemResponseDTO createSaleItem(SaleItemRequestDTO request);

        Page<SaleItemResponseDTO> getAllSaleItems(
            int page,
            int size,
            String sortBy,
            String sortDir
        );

    SaleItemResponseDTO getSaleItemById(Integer saleItemId);

    SaleItemResponseDTO updateSaleItem(Integer saleItemId, SaleItemRequestDTO request);

    void deleteSaleItem(Integer saleItemId);

}