package com.inventory_management.controller;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;
import com.inventory_management.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @PostMapping
        @Operation(summary = "Create a new user", description = "Creates a new user in the inventory")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request
    ) {

        UserResponseDTO response = userService.createUser(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the inventory")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
        @Operation(summary = "Get user by ID", description = "Retrieves a user by its ID")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID", description = "Updates an existing user by its ID")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Integer id,
            @RequestBody UserRequestDTO request
    ) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
        @Operation(summary = "Delete user by ID", description = "Deletes a user by its ID")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id
    ) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

}