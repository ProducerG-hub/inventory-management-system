package com.inventory_management.service.impl;

import com.inventory_management.dto.profile.ChangePasswordRequestDTO;
import com.inventory_management.dto.profile.ProfileResponseDTO;
import com.inventory_management.dto.profile.UpdateProfileRequestDTO;
import com.inventory_management.entity.User;
import com.inventory_management.security.CustomUserDetails;
import com.inventory_management.service.FileStorageService;
import com.inventory_management.service.ProfileService;
import org.springframework.transaction.annotation.Transactional;
import com.inventory_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;


    // ==========================================
    // GET CURRENT AUTHENTICATED USER
    // ==========================================

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }


    // ==========================================
    // GET MY PROFILE
    // ==========================================

    @Override
    public ProfileResponseDTO getMyProfile() {

        User user = getAuthenticatedUser();

        return mapToProfileResponse(user);
    }


    // ==========================================
    // UPDATE MY PROFILE
    // ==========================================

    @Transactional
    @Override
    public ProfileResponseDTO updateMyProfile(
            UpdateProfileRequestDTO request
    ) {

        User user = getAuthenticatedUser();

        user.setFullName(request.getFullName());

        User updatedUser = userRepository.save(user);

        return mapToProfileResponse(updatedUser);
    }


    // ==========================================
    // CHANGE PASSWORD
    // ==========================================

    @Transactional
    @Override
    public void changePassword(
            ChangePasswordRequestDTO request
    ) {

        User user = getAuthenticatedUser();


        // Check current password

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        )) {

            throw new IllegalArgumentException(
                    "Current password is incorrect"
            );
        }


        // Check new password confirmation

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new IllegalArgumentException(
                    "New passwords do not match"
            );

        }


        // Prevent same password

        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword()
        )) {

            throw new IllegalArgumentException(
                    "New password must be different from current password"
            );
        }


        // Hash new password

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );
        userRepository.save(user);
    }

    @Transactional
    @Override
    public ProfileResponseDTO uploadProfilePicture(
            MultipartFile file
    ) {

        User user = getAuthenticatedUser();

        String oldPicture =
                user.getProfilePicture();

        String newPicture =
                fileStorageService.storeProfilePicture(file);


        // Update database reference

        user.setProfilePicture(newPicture);

        User updatedUser =
                userRepository.save(user);


        // Delete old picture AFTER successful save

        if (oldPicture != null &&
                !oldPicture.isBlank()) {

            fileStorageService.deleteProfilePicture(
                    oldPicture
            );
        }

        return mapToProfileResponse(updatedUser);
    }

    @Transactional
    @Override
    public void deleteProfilePicture() {

        User user = getAuthenticatedUser();

        String picture =
                user.getProfilePicture();

        if (picture == null ||
                picture.isBlank()) {

            return;
        }

        fileStorageService.deleteProfilePicture(
                picture
        );

        user.setProfilePicture(null);

        userRepository.save(user);
    }


    // ==========================================
    // MAPPER
    // ==========================================

    private ProfileResponseDTO mapToProfileResponse(
            User user
    ) {

        return new ProfileResponseDTO(

                user.getUserId(),

                user.getFullName(),

                user.getEmail(),

                user.getRole(),

                user.getProfilePicture(),

                user.getIsActive(),

                user.getCreatedAt()

        );
    }
}
