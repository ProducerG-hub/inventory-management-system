package com.inventory_management.controller;

import com.inventory_management.dto.request.LoginRequestDTO;
import com.inventory_management.dto.response.LoginResponseDTO;
import com.inventory_management.service.AuthService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(

            @Valid @RequestBody LoginRequestDTO request

    ) {

        return ResponseEntity.ok(

                authService.login(request)

        );

    }

}