package com.inventory_management.controller;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;
import com.inventory_management.service.UserService;

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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create User")
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserRequestDTO request
    ) {

        return new ResponseEntity<>(
                userService.createUser(request),
                HttpStatus.CREATED
        );

    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get Users")
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(

            @RequestParam(defaultValue = "true")
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "userId")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir

    ) {

        return ResponseEntity.ok(

                userService.getAllUsers(
                        active,
                        page,
                        size,
                        sortBy,
                        sortDir
                )

        );

    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Search Users")
    public ResponseEntity<Page<UserResponseDTO>> searchUsers(

            @RequestParam
            String keyword,

            @RequestParam(defaultValue = "true")
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "userId")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir

    ) {

        return ResponseEntity.ok(

                userService.searchUsers(
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
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get User By ID")
    public ResponseEntity<UserResponseDTO> getUserById(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                userService.getUserById(id)
        );

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update User")
    public ResponseEntity<UserResponseDTO> updateUser(

            @PathVariable Integer id,

            @RequestBody UserRequestDTO request

    ) {

        return ResponseEntity.ok(

                userService.updateUser(
                        id,
                        request
                )

        );

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deactivate User")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Integer id
    ) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Restore User")
    public ResponseEntity<Void> restoreUser(
            @PathVariable Integer id
    ) {

        userService.restoreUser(id);

        return ResponseEntity.ok().build();

    }

}