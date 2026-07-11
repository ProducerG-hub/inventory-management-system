package com.inventory_management.service.impl;

import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;
import com.inventory_management.entity.Category;
import com.inventory_management.mapper.CategoryMapper;
import com.inventory_management.repository.CategoryRepository;
import com.inventory_management.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;


    @Override
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {

        Category category = categoryMapper.toEntity(request);

        Category savedCategory = categoryRepository.save(category);
        logger.info("Category created: {}", savedCategory.getCategoryName());

        return categoryMapper.toResponse(savedCategory);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> getAllCategories(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponse);
    }
    

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(Integer categoryId) {
        logger.info("Fetching category by ID: {}", categoryId);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found")
                );

        return categoryMapper.toResponse(category);
    }


    @Override
    @Transactional
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

                logger.info("Category updated: {}", updatedCategory.getCategoryName());
        return categoryMapper.toResponse(updatedCategory);
    }


    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) {
        logger.info("Deleting category by ID: {}", categoryId);

        if(!categoryRepository.existsById(categoryId)){
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(categoryId);
        logger.info("Category deleted: {}", categoryId);
    }

}