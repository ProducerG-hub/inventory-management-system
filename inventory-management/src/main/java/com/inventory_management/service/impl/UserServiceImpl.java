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

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.audit.AuditEventType;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.service.AuditEventPublisher;
import com.inventory_management.security.CustomUserDetails;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


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
    private final ObjectMapper objectMapper;
    private final AuditEventPublisher auditEventPublisher;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();
        }
        else {
            throw new IllegalStateException("Authenticated user not found");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setIsActive(true);
        user.setDeletedAt(null);

        Map<String, Object> newValues = new HashMap<>();
        newValues.put("fullName", request.getFullName());
        newValues.put("email", request.getEmail());
        newValues.put("role", request.getRole());
        newValues.put("isActive", user.getIsActive());

        String newValuesJson;

        try{
                newValuesJson = objectMapper.writeValueAsString(newValues);
        } catch (JsonProcessingException e) {
                throw new RuntimeException("Error converting new values to JSON", e);
        }

        User savedUser = userRepository.save(user);

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.CREATE)
                        .entityType(AuditEntityType.USER)
                        .entityId(savedUser.getUserId())
                        .description("User created successfully")
                        .newValues(newValuesJson)
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();
        }
        else {
            throw new IllegalStateException("Authenticated user not found");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setIsActive(false);
        existingUser.setDeletedAt(LocalDateTime.now());

        userRepository.save(existingUser);

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.DEACTIVATE)
                        .entityType(AuditEntityType.USER)
                        .entityId(existingUser.getUserId())
                        .description("User deactivated successfully")
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

        logger.info("User deactivated: {}",
                existingUser.getEmail());

    }

    @Override
    public void restoreUser(Integer userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();
        }
        else {
            throw new IllegalStateException("Authenticated user not found");
        }

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        existingUser.setIsActive(true);
        existingUser.setDeletedAt(null);

        userRepository.save(existingUser);

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.ACTIVATE)
                        .entityType(AuditEntityType.USER)
                        .entityId(existingUser.getUserId())
                        .description("User activated successfully")
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

        logger.info("User restored: {}",
                existingUser.getEmail());

    }

}