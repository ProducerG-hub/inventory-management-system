package com.inventory_management.service;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);

    Page<UserResponseDTO> getAllUsers(
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<UserResponseDTO> searchUsers(
            String keyword,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    UserResponseDTO getUserById(Integer userId);

    UserResponseDTO updateUser(
            Integer userId,
            UserRequestDTO request
    );

    void deleteUser(Integer userId);

    void restoreUser(Integer userId);

}