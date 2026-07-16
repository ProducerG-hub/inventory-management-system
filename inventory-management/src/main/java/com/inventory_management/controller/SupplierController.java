package com.inventory_management.controller;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.service.SupplierService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "Supplier management APIs")
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create supplier",
            description = "Creates a new supplier"
    )
    public ResponseEntity<SupplierResponseDTO> createSupplier(
            @Valid @RequestBody SupplierRequestDTO request
    ) {

        SupplierResponseDTO response =
                supplierService.createSupplier(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );

    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(
            summary = "Get suppliers",
            description = "Retrieves active or inactive suppliers"
    )
    public ResponseEntity<Page<SupplierResponseDTO>> getAllSuppliers(

            @RequestParam(defaultValue = "true")
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "supplierId")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir

    ) {

        return ResponseEntity.ok(

                supplierService.getAllSuppliers(
                        active,
                        page,
                        size,
                        sortBy,
                        sortDir
                )

        );

    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(
            summary = "Search suppliers",
            description = "Search suppliers by keyword"
    )
    public ResponseEntity<Page<SupplierResponseDTO>> searchSuppliers(

            @RequestParam
            String keyword,

            @RequestParam(defaultValue = "true")
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "supplierId")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir

    ) {

        return ResponseEntity.ok(

                supplierService.searchSuppliers(
                        keyword,
                        active,
                        page,
                        size,
                        sortBy,
                        sortDir
                )

        );

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(
            summary = "Get supplier by ID",
            description = "Retrieves supplier by ID"
    )
    public ResponseEntity<SupplierResponseDTO> getSupplierById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                supplierService.getSupplierById(id)
        );

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update supplier",
            description = "Updates supplier"
    )
    public ResponseEntity<SupplierResponseDTO> updateSupplier(

            @PathVariable Integer id,

            @Valid
            @RequestBody SupplierRequestDTO request

    ) {

        return ResponseEntity.ok(

                supplierService.updateSupplier(
                        id,
                        request
                )

        );

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Deactivate supplier",
            description = "Soft deletes supplier"
    )
    public ResponseEntity<Void> deleteSupplier(
            @PathVariable Integer id
    ) {

        supplierService.deleteSupplier(id);

        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Restore supplier",
            description = "Restores a soft deleted supplier"
    )
    public ResponseEntity<Void> restoreSupplier(
            @PathVariable Integer id
    ) {

        supplierService.restoreSupplier(id);

        return ResponseEntity.noContent().build();

    }

}