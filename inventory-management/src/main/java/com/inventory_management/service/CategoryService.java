package com.inventory_management.service;

import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO request);

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Integer categoryId);

    CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO request);

    void deleteCategory(Integer categoryId);
}