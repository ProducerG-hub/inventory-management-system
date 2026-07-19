package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDTO {

    private String message;

    private String token;

    private Integer userId;

    private String fullName;

    private String email;

    private String role;

}