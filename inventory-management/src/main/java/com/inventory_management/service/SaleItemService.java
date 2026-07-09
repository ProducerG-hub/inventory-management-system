package com.inventory_management.service;

import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.response.SaleItemResponseDTO;

import java.util.List;

public interface SaleItemService {

    SaleItemResponseDTO createSaleItem(SaleItemRequestDTO request);

    List<SaleItemResponseDTO> getAllSaleItems();

    SaleItemResponseDTO getSaleItemById(Integer saleItemId);

    SaleItemResponseDTO updateSaleItem(Integer saleItemId, SaleItemRequestDTO request);

    void deleteSaleItem(Integer saleItemId);

}