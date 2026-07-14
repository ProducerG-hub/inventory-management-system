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
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product", description = "Creates a new product in the inventory")
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request
    ) {

        ProductResponseDTO response = productService.createProduct(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Get all active products", description = "Retrieves a list of all active products in the inventory with pagination and sorting")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "productId") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir

    ) {

        return ResponseEntity.ok(

                productService.getActiveProducts(

                        page,

                        size,

                        sortBy,

                        sortDir

                )

        );

    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<Page<ProductResponseDTO>>

    searchProducts(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "productId") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir

    ){

        return ResponseEntity.ok(

                productService.searchProducts(

                        keyword,

                        page,

                        size,

                        sortBy,

                        sortDir

                )

        );

    }

        @GetMapping("/inactive")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Get all inactive products", description = "Retrieves a list of all inactive products in the inventory with pagination and sorting")
        public ResponseEntity<Page<ProductResponseDTO>>
        getInactiveProducts(

                @RequestParam(defaultValue = "0") int page,

                @RequestParam(defaultValue = "10") int size,

                @RequestParam(defaultValue = "productId") String sortBy,

                @RequestParam(defaultValue = "asc") String sortDir

        ){

        return ResponseEntity.ok(

                productService.getInactiveProducts(

                        page,

                        size,

                        sortBy,

                        sortDir

                )

        );

        }

        @PatchMapping("/{id}/restore")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Restore product", description = "Restores an inactive product by its ID")
        public ResponseEntity<Void> restoreProduct(
        @PathVariable Integer id
        ){

        productService.restoreProduct(id);

        return ResponseEntity.ok().build();

        }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN','STAFF')")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its ID")
    public ResponseEntity<ProductResponseDTO> getProductById(
           @Valid @PathVariable Integer id
    ) {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product by ID", description = "Updates an existing product by its ID")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductRequestDTO request
    ) {

        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product by ID", description = "Deletes a product by its ID")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Integer id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}