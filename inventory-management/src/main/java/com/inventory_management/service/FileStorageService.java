package com.inventory_management.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String storeProfilePicture(MultipartFile file);

    void deleteProfilePicture(String filePath);
}