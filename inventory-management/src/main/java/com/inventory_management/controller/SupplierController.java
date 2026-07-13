package com.inventory_management.controller;


import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.service.SupplierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;


@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "Supplier management APIs")
public class SupplierController {


    private final SupplierService supplierService;



    @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new supplier", description = "Creates a new supplier in the inventory")
    public ResponseEntity<SupplierResponseDTO> createSupplier(
            @Valid @RequestBody SupplierRequestDTO request
    ){

        return new ResponseEntity<>(
                supplierService.createSupplier(request),
                HttpStatus.CREATED
        );

    }



    @GetMapping
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
        @Operation(summary = "Get all suppliers", description = "Retrieves a paginated list of all suppliers in the inventory")
        public ResponseEntity<Page<SupplierResponseDTO>> getAllSuppliers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "supplierId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
        ){

        return ResponseEntity.ok(
            supplierService.getAllSuppliers(page, size, sortBy, sortDir)
        );

    }

    @GetMapping("/search")
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
        @Operation(summary = "Search suppliers", description = "Searches for suppliers by keyword with pagination and sorting")
        public ResponseEntity<Page<SupplierResponseDTO>> searchSuppliers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "supplierId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
        ){

        return ResponseEntity.ok(
            supplierService.searchSuppliers(keyword, page, size, sortBy, sortDir)
        );

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Get supplier by ID", description = "Retrieves a supplier by its ID")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );

    }



    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Update supplier by ID", description = "Updates an existing supplier by its ID")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(
            @PathVariable Integer id,
            @RequestBody SupplierRequestDTO request
    ){

        return ResponseEntity.ok(
                supplierService.updateSupplier(id, request)
        );

    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete supplier by ID", description = "Deletes a supplier by its ID")
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Integer id
    ){

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();

    }

}