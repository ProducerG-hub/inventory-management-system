package com.inventory_management.service;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;

import java.util.List;

public interface StockMovementService {

    StockMovementResponseDTO createStockMovement(StockMovementRequestDTO request);

    List<StockMovementResponseDTO> getAllStockMovements();

    StockMovementResponseDTO getStockMovementById(Integer movementId);

    StockMovementResponseDTO updateStockMovement(Integer movementId, StockMovementRequestDTO request);

    void deleteStockMovement(Integer movementId);

}