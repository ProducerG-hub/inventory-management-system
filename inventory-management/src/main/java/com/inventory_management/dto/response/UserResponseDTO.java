package com.inventory_management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private Integer userId;

    private String fullName;

    private String email;

    private String role;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

}