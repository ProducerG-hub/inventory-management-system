package com.inventory_management.controller;

import com.inventory_management.dto.request.ProductRequestDTO;
import com.inventory_management.dto.response.ProductResponseDTO;
import com.inventory_management.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Creates a new product in the inventory")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {

        ProductResponseDTO response = productService.createProduct(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all products in the inventory")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    public ResponseEntity<ProductResponseDTO> getProductById(
           @Valid @PathVariable Integer id
    ) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID", description = "Updates an existing product by its ID")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductRequestDTO request
    ) {

        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Integer id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}