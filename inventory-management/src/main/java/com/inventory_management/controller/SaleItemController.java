package com.inventory_management.controller;

import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.response.SaleItemResponseDTO;
import com.inventory_management.service.SaleItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale-items")
@RequiredArgsConstructor
public class SaleItemController {

    private final SaleItemService saleItemService;

    @PostMapping
    public ResponseEntity<SaleItemResponseDTO> createSaleItem(
            @RequestBody SaleItemRequestDTO request
    ) {

        SaleItemResponseDTO response = saleItemService.createSaleItem(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SaleItemResponseDTO>> getAllSaleItems() {

        return ResponseEntity.ok(saleItemService.getAllSaleItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleItemResponseDTO> getSaleItemById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(saleItemService.getSaleItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SaleItemResponseDTO> updateSaleItem(
            @PathVariable Integer id,
            @RequestBody SaleItemRequestDTO request
    ) {

        return ResponseEntity.ok(saleItemService.updateSaleItem(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaleItem(
            @PathVariable Integer id
    ) {

        saleItemService.deleteSaleItem(id);

        return ResponseEntity.noContent().build();
    }

}