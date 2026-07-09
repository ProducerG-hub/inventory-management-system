package com.inventory_management.controller;


import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.service.SupplierService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {


    private final SupplierService supplierService;



    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(
            @Valid @RequestBody SupplierRequestDTO request
    ){

        return new ResponseEntity<>(
                supplierService.createSupplier(request),
                HttpStatus.CREATED
        );

    }



    @GetMapping
    public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){

        return ResponseEntity.ok(
                supplierService.getAllSuppliers()
        );

    }



    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );

    }



    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(
            @PathVariable Integer id,
            @RequestBody SupplierRequestDTO request
    ){

        return ResponseEntity.ok(
                supplierService.updateSupplier(id, request)
        );

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Integer id
    ){

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();

    }

}