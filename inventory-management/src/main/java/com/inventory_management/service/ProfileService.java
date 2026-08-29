package com.inventory_management.service;

import com.inventory_management.dto.profile.ProfileResponseDTO;
import com.inventory_management.dto.profile.UpdateProfileRequestDTO;
import com.inventory_management.dto.profile.ChangePasswordRequestDTO;
import org.springframework.web.multipart.MultipartFile;


public interface ProfileService {
    ProfileResponseDTO getMyProfile();

    ProfileResponseDTO updateMyProfile(
            UpdateProfileRequestDTO request
    );

    void changePassword(
            ChangePasswordRequestDTO request
    );

    ProfileResponseDTO uploadProfilePicture(MultipartFile file);

    void deleteProfilePicture();
}
