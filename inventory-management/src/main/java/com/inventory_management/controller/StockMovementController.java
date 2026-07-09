package com.inventory_management.controller;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;
import com.inventory_management.service.StockMovementService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(
            @RequestBody StockMovementRequestDTO request
    ) {

        StockMovementResponseDTO response = stockMovementService.createStockMovement(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {

        return ResponseEntity.ok(stockMovementService.getAllStockMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(stockMovementService.getStockMovementById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockMovementResponseDTO> updateStockMovement(
            @PathVariable Integer id,
            @RequestBody StockMovementRequestDTO request
    ) {

        return ResponseEntity.ok(stockMovementService.updateStockMovement(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockMovement(
            @PathVariable Integer id
    ) {

        stockMovementService.deleteStockMovement(id);

        return ResponseEntity.noContent().build();
    }

}