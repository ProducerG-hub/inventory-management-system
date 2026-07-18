package com.inventory_management.service.impl;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.UserMapper;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.UserService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger =
            LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {

        User user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setIsActive(true);
        user.setDeletedAt(null);

        User savedUser = userRepository.save(user);

        logger.info("User created: {}", savedUser.getEmail());

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Fetching {} users",
                active ? "active" : "inactive");

        return userRepository
                .findByIsActive(active, pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> searchUsers(
            String keyword,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Searching users: {}", keyword);

        return userRepository
                .searchUsers(keyword, active, pageable)
                .map(userMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        logger.info("Fetching user ID: {}", userId);

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(
            Integer userId,
            UserRequestDTO request
    ) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setFullName(request.getFullName());
        existingUser.setEmail(request.getEmail());
        existingUser.setRole(request.getRole());
        existingUser.setIsActive(request.getIsActive());

        /*
         * Update password only if supplied
         */
        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            existingUser.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

        }

        User updatedUser =
                userRepository.save(existingUser);

        logger.info("User updated: {}",
                updatedUser.getEmail());

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Integer userId) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setIsActive(false);
        existingUser.setDeletedAt(LocalDateTime.now());

        userRepository.save(existingUser);

        logger.info("User deactivated: {}",
                existingUser.getEmail());

    }

    @Override
    public void restoreUser(Integer userId) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setIsActive(true);
        existingUser.setDeletedAt(null);

        userRepository.save(existingUser);

        logger.info("User restored: {}",
                existingUser.getEmail());

    }

}