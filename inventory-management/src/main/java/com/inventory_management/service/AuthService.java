package com.inventory_management.service;

import com.inventory_management.dto.request.LoginRequestDTO;
import com.inventory_management.dto.response.LoginResponseDTO;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO request);

}