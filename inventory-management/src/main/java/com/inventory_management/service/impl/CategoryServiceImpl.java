package com.inventory_management.service.impl;

import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;
import com.inventory_management.entity.Category;
import com.inventory_management.mapper.CategoryMapper;
import com.inventory_management.repository.CategoryRepository;
import com.inventory_management.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }


    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryResponseDTO getCategoryById(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found")
                );

        return categoryMapper.toResponse(category);
    }


    @Override
    public CategoryResponseDTO updateCategory(
            Integer categoryId,
            CategoryRequestDTO request
    ) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found")
                );


        existingCategory.setCategoryName(request.getCategoryName());
        existingCategory.setDescription(request.getDescription());


        Category updatedCategory =
                categoryRepository.save(existingCategory);


        return categoryMapper.toResponse(updatedCategory);
    }


    @Override
    public void deleteCategory(Integer categoryId) {

        if(!categoryRepository.existsById(categoryId)){
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(categoryId);
    }

}