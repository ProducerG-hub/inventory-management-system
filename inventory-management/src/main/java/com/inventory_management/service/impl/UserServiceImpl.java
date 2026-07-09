package com.inventory_management.service.impl;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.UserMapper;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {

        User user = userMapper.toEntity(request);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDTO getUserById(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(
            Integer userId,
            UserRequestDTO request
    ) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        existingUser.setFullName(request.getFullName());
        existingUser.setPassword(request.getPassword());
        existingUser.setEmail(request.getEmail());
        existingUser.setRole(request.getRole());
        existingUser.setIsActive(request.getIsActive());

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
    }

}