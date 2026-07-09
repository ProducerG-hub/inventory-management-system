package com.inventory_management.service;

import com.inventory_management.dto.request.SaleRequestDTO;
import com.inventory_management.dto.response.SaleResponseDTO;

import java.util.List;

public interface SaleService {

    SaleResponseDTO createSale(SaleRequestDTO request);

    List<SaleResponseDTO> getAllSales();

    SaleResponseDTO getSaleById(Integer saleId);

    SaleResponseDTO updateSale(Integer saleId, SaleRequestDTO request);

    void deleteSale(Integer saleId);

}