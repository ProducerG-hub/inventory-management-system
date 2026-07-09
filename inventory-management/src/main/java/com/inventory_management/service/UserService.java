package com.inventory_management.service;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUserById(Integer userId);

    UserResponseDTO updateUser(Integer userId, UserRequestDTO request);

    void deleteUser(Integer userId);

}