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

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "Supplier management APIs")
public class SupplierController {


    private final SupplierService supplierService;



    @PostMapping
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
        @Operation(summary = "Get all suppliers", description = "Retrieves a list of all suppliers in the inventory")
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){

        return ResponseEntity.ok(
                supplierService.getAllSuppliers()
        );

    }



    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID", description = "Retrieves a supplier by its ID")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );

    }



    @PutMapping("/{id}")
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
    @Operation(summary = "Delete supplier by ID", description = "Deletes a supplier by its ID")
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Integer id
    ){

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();

    }

}