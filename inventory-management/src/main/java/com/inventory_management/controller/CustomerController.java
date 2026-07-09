package com.inventory_management.controller;

import com.inventory_management.dto.request.CustomerRequestDTO;
import com.inventory_management.dto.response.CustomerResponseDTO;
import com.inventory_management.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Customer management APIs")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
        @Operation(summary = "Create a new customer", description = "Creates a new customer in the inventory")
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @Valid @RequestBody CustomerRequestDTO request
    ) {

        CustomerResponseDTO response = customerService.createCustomer(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers in the inventory")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {

        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
        @Operation(summary = "Get customer by ID", description = "Retrieves a customer by its ID")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer by ID", description = "Updates an existing customer by its ID")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @PathVariable Integer id,
            @RequestBody CustomerRequestDTO request
    ) {

        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Delete customer by ID", description = "Deletes a customer by its ID")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer id
    ) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

}