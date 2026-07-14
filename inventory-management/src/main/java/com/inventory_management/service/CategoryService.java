package com.inventory_management.service;

import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;

import org.springframework.data.domain.Page;


public interface CategoryService {


    CategoryResponseDTO createCategory(CategoryRequestDTO request);


    Page<CategoryResponseDTO> getAllCategories(
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    );


    Page<CategoryResponseDTO> searchCategories(
            String keyword,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    );


    CategoryResponseDTO getCategoryById(Integer categoryId);


    CategoryResponseDTO updateCategory(
            Integer categoryId,
            CategoryRequestDTO request
    );


    void deleteCategory(Integer categoryId);


    CategoryResponseDTO restoreCategory(Integer categoryId);

}