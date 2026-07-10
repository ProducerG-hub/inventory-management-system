package com.inventory_management.controller;

import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.response.SaleItemResponseDTO;
import com.inventory_management.service.SaleItemService;

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
@RequestMapping("/api/sale-items")
@RequiredArgsConstructor
@Tag(name = "Sale Items", description = "Sale item management APIs")
public class SaleItemController {

    private final SaleItemService saleItemService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Create a new sale item", description = "Creates a new sale item in the inventory")
    public ResponseEntity<SaleItemResponseDTO> createSaleItem(
            @Valid @RequestBody SaleItemRequestDTO request
    ) {

        SaleItemResponseDTO response = saleItemService.createSaleItem(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN','STAFF')")
        @Operation(summary = "Get all sale items", description = "Retrieves a list of all sale items in the inventory")
    public ResponseEntity<List<SaleItemResponseDTO>> getAllSaleItems() {

        return ResponseEntity.ok(saleItemService.getAllSaleItems());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','STAFF')")
        @Operation(summary = "Get sale item by ID", description = "Retrieves a sale item by its ID")
    public ResponseEntity<SaleItemResponseDTO> getSaleItemById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(saleItemService.getSaleItemById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update sale item by ID", description = "Updates an existing sale item by its ID")
    public ResponseEntity<SaleItemResponseDTO> updateSaleItem(
            @PathVariable Integer id,
            @RequestBody SaleItemRequestDTO request
    ) {

        return ResponseEntity.ok(saleItemService.updateSaleItem(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Delete sale item by ID", description = "Deletes a sale item by its ID")
    public ResponseEntity<Void> deleteSaleItem(
            @PathVariable Integer id
    ) {

        saleItemService.deleteSaleItem(id);

        return ResponseEntity.noContent().build();
    }

}