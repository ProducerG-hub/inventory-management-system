package com.inventory_management.service.impl;

import com.inventory_management.dto.request.LoginRequestDTO;
import com.inventory_management.dto.response.LoginResponseDTO;
import com.inventory_management.entity.User;
import com.inventory_management.security.CustomUserDetails;
import com.inventory_management.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.inventory_management.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(

                                request.getEmail(),

                                request.getPassword()

                        )

                );

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();
        String token = jwtService.generateToken(userDetails);

        return new LoginResponseDTO(

                "Login successful",
                token,

                user.getUserId(),
                
                user.getFullName(),


                user.getEmail(),

                user.getRole()

        );

    }

}