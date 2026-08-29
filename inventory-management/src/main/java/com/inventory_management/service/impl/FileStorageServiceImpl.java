package com.inventory_management.service.impl;

import com.inventory_management.service.FileStorageService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.io.InputStream;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl
        implements FileStorageService {

    private static final long MAX_FILE_SIZE =
            1024 * 1024; // 1MB

    private static final String UPLOAD_DIR =
            "uploads/profile_pictures";

    private static final Set<String> ALLOWED_TYPES =
            Set.of(
                    "image/jpeg",
                    "image/png",
                    "image/webp"
            );


    @Override
    public String storeProfilePicture(
            MultipartFile file
    ) {

        // ==============================
        // Validate empty file
        // ==============================

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "Profile picture is required"
            );
        }


        // ==============================
        // Validate file size
        // ==============================

        if (file.getSize() > MAX_FILE_SIZE) {

            throw new IllegalArgumentException(
                    "Profile picture must not exceed 1MB"
            );
        }


        // ==============================
        // Validate content type
        // ==============================

        String contentType =
                file.getContentType();

        if (contentType == null ||
                !ALLOWED_TYPES.contains(contentType)) {

            throw new IllegalArgumentException(
                    "Only JPEG, PNG and WebP images are allowed"
            );
        }
        try (InputStream inputStream = file.getInputStream()) {

            BufferedImage image =
                    ImageIO.read(inputStream);

            if (image == null) {

                throw new IllegalArgumentException(
                        "Invalid image file"
                );
            }

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to validate image",
                    e
            );
        }


        // ==============================
        // Generate unique filename
        // ==============================

        String originalFilename =
                file.getOriginalFilename();

        String extension =
                getExtension(originalFilename);

        String filename =
                UUID.randomUUID()
                        + extension;


        // ==============================
        // Create directory
        // ==============================

        try {

            Path uploadPath =
                    Paths.get(UPLOAD_DIR)
                            .toAbsolutePath()
                            .normalize();

            Files.createDirectories(uploadPath);


            // ==============================
            // Save file
            // ==============================

            Path targetPath =
                    uploadPath.resolve(filename)
                            .normalize();

            file.transferTo(targetPath);


            // Return DB reference
            return "/uploads/profile_pictures/"
                    + filename;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to store profile picture",
                    e
            );
        }
    }


    @Override
    public void deleteProfilePicture(
            String filePath
    ) {

        if (filePath == null ||
                filePath.isBlank()) {

            return;
        }

        try {

            Path path = Paths.get(
                    filePath.startsWith("/")
                            ? filePath.substring(1)
                            : filePath
            );
            Files.deleteIfExists(path);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to delete profile picture",
                    e
            );
        }
    }


    // ==========================================
    // FILE EXTENSION
    // ==========================================

    private String getExtension(
            String filename
    ) {

        if (filename == null ||
                !filename.contains(".")) {

            throw new IllegalArgumentException(
                    "Invalid image filename"
            );
        }

        return filename.substring(
                filename.lastIndexOf(".")
        ).toLowerCase();
    }
}