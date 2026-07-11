package com.inventory_management.service.impl;

import com.inventory_management.dto.request.UserRequestDTO;
import com.inventory_management.dto.response.UserResponseDTO;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.UserMapper;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        logger.info("User created: {}", savedUser);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
        public Page<UserResponseDTO> getAllUsers(
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Fetching all users");

        return userRepository.findAll(pageable)
            .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> searchUsers(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Searching users with keyword: {}", keyword);

        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Integer userId) {
        logger.info("Fetching user by ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        logger.info("Fetching user by ID: {}", userId);
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
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

        logger.info("User updated: {}", updatedUser);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        logger.info("Deleting user by ID: {}", userId);

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteById(userId);
    }

}