package com.inventory_management.controller;

import com.inventory_management.dto.profile.ChangePasswordRequestDTO;
import com.inventory_management.dto.profile.ProfileResponseDTO;
import com.inventory_management.dto.profile.UpdateProfileRequestDTO;
import com.inventory_management.service.ProfileService;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    // ==========================================
    // GET MY PROFILE
    // ==========================================

    @GetMapping("/me")
    public ResponseEntity<ProfileResponseDTO> getMyProfile() {

        return ResponseEntity.ok(
                profileService.getMyProfile()
        );
    }


    // ==========================================
    // UPDATE MY PROFILE
    // ==========================================

    @PutMapping("/me")
    public ResponseEntity<ProfileResponseDTO> updateMyProfile(
            @Valid @RequestBody UpdateProfileRequestDTO request
    ) {

        return ResponseEntity.ok(
                profileService.updateMyProfile(request)
        );
    }


    // ==========================================
    // CHANGE PASSWORD
    // ==========================================

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO request
    ) {

        profileService.changePassword(request);

        return ResponseEntity.noContent().build();
    }

    // ==========================================
// UPLOAD PROFILE PICTURE
// ==========================================

    @PostMapping(
            value = "/me/picture",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ProfileResponseDTO>
    uploadProfilePicture(
            @RequestParam("file")
            MultipartFile file
    ) {

        return ResponseEntity.ok(
                profileService.uploadProfilePicture(file)
        );
    }


// ==========================================
// DELETE PROFILE PICTURE
// ==========================================

    @DeleteMapping("/me/picture")
    public ResponseEntity<Void>
    deleteProfilePicture() {

        profileService.deleteProfilePicture();

        return ResponseEntity.noContent().build();
    }
}