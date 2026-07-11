package com.inventory_management.controller;

import com.inventory_management.dto.request.SaleRequestDTO;
import com.inventory_management.dto.response.SaleResponseDTO;
import com.inventory_management.service.SaleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
@Tag(name = "Sales", description = "Sale management APIs")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Create a new sale", description = "Creates a new sale in the inventory")
    public ResponseEntity<SaleResponseDTO> createSale(
            @Valid @RequestBody SaleRequestDTO request
    ) {

        SaleResponseDTO response = saleService.createSale(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN','STAFF')")
    @Operation(summary = "Get all sales", description = "Retrieves a paginated list of all sales in the inventory")
    public ResponseEntity<Page<SaleResponseDTO>> getAllSales(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "saleId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        return ResponseEntity.ok(saleService.getAllSales(page, size, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','STAFF')")
        @Operation(summary = "Get sale by ID", description = "Retrieves a sale by its ID")
    public ResponseEntity<SaleResponseDTO> getSaleById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update sale by ID", description = "Updates an existing sale by its ID")
    public ResponseEntity<SaleResponseDTO> updateSale(
            @PathVariable Integer id,
            @RequestBody SaleRequestDTO request
    ) {

        return ResponseEntity.ok(saleService.updateSale(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Delete sale by ID", description = "Deletes a sale by its ID")
    public ResponseEntity<Void> deleteSale(
            @PathVariable Integer id
    ) {

        saleService.deleteSale(id);

        return ResponseEntity.noContent().build();
    }

}