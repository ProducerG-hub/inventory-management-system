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
import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.service.AuditEventPublisher;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuditEventPublisher auditEventPublisher;

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
        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(user)
                        .action(AuditAction.LOGIN_SUCCESS)
                        .entityType(AuditEntityType.AUTHENTICATION)
                        .entityId(null)
                        .description("User logged in successfully")
                        .build()
        );

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