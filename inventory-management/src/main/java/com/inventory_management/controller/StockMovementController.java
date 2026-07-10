package com.inventory_management.controller;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;
import com.inventory_management.service.StockMovementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;   

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
@Tag(name = "Stock Movements", description = "Stock movement management APIs")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Create a new stock movement", description = "Creates a new stock movement in the inventory")
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(
            @Valid @RequestBody StockMovementRequestDTO request
    ) {

        StockMovementResponseDTO response = stockMovementService.createStockMovement(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN','STAFF')")
    @Operation(summary = "Get all stock movements", description = "Retrieves a list of all stock movements in the inventory")
    public ResponseEntity<List<StockMovementResponseDTO>> getAllStockMovements() {

        return ResponseEntity.ok(stockMovementService.getAllStockMovements());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','STAFF')")
        @Operation(summary = "Get stock movement by ID", description = "Retrieves a stock movement by its ID")
    public ResponseEntity<StockMovementResponseDTO> getStockMovementById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(stockMovementService.getStockMovementById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update stock movement by ID", description = "Updates an existing stock movement by its ID")
    public ResponseEntity<StockMovementResponseDTO> updateStockMovement(
            @PathVariable Integer id,
            @RequestBody StockMovementRequestDTO request
    ) {

        return ResponseEntity.ok(stockMovementService.updateStockMovement(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Delete stock movement by ID", description = "Deletes a stock movement by its ID")
    public ResponseEntity<Void> deleteStockMovement(
            @PathVariable Integer id
    ) {

        stockMovementService.deleteStockMovement(id);

        return ResponseEntity.noContent().build();
    }

}