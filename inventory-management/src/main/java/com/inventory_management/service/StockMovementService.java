package com.inventory_management.service;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;
import org.springframework.data.domain.Page;

public interface StockMovementService {

    StockMovementResponseDTO createStockMovement(StockMovementRequestDTO request);

        Page<StockMovementResponseDTO> getAllStockMovements(
            int page,
            int size,
            String sortBy,
            String sortDir
        );

    StockMovementResponseDTO getStockMovementById(Integer movementId);

    StockMovementResponseDTO updateStockMovement(Integer movementId, StockMovementRequestDTO request);

    void deleteStockMovement(Integer movementId);

}