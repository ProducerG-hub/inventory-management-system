package com.inventory_management.service.impl;


import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;
import com.inventory_management.entity.Category;
import com.inventory_management.mapper.CategoryMapper;
import com.inventory_management.repository.CategoryRepository;
import com.inventory_management.service.CategoryService;


import lombok.RequiredArgsConstructor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {


    private static final Logger logger =
            LoggerFactory.getLogger(CategoryServiceImpl.class);


    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;



    // ================= CREATE CATEGORY =================

    @Override
    @Transactional
    public CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    ) {


        if(categoryRepository
                .existsByCategoryNameIgnoreCaseAndActiveTrue(
                        request.getCategoryName()
                )){

            throw new RuntimeException(
                    "Category already exists"
            );
        }



        Category category =
                categoryMapper.toEntity(request);



        category.setActive(true);

        category.setDeletedAt(null);



        Category savedCategory =
                categoryRepository.save(category);



        logger.info(
                "Category created: {}",
                savedCategory.getCategoryName()
        );



        return categoryMapper.toResponse(savedCategory);
    }




    // ================= GET ALL CATEGORIES =================

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> getAllCategories(
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){


        Sort sort =
                sortDir.equalsIgnoreCase("desc")
                ?
                Sort.by(sortBy).descending()
                :
                Sort.by(sortBy).ascending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );



        return categoryRepository
                .findByActive(
                        active,
                        pageable
                )
                .map(categoryMapper::toResponse);

    }





    // ================= SEARCH CATEGORY =================

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryResponseDTO> searchCategories(
            String keyword,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){


        Sort sort =
                sortDir.equalsIgnoreCase("desc")
                ?
                Sort.by(sortBy).descending()
                :
                Sort.by(sortBy).ascending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );



        return categoryRepository
                .searchCategories(
                        keyword,
                        active,
                        pageable
                )
                .map(categoryMapper::toResponse);

    }




    // ================= GET CATEGORY BY ID =================

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDTO getCategoryById(
            Integer categoryId
    ){

        logger.info(
                "Fetching category by ID: {}",
                categoryId
        );



        Category category =
                categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );



        return categoryMapper.toResponse(category);
    }





    // ================= UPDATE CATEGORY =================

    @Override
    @Transactional
    public CategoryResponseDTO updateCategory(
            Integer categoryId,
            CategoryRequestDTO request
    ){


        Category existingCategory =
                categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );



        existingCategory.setCategoryName(
                request.getCategoryName()
        );


        existingCategory.setDescription(
                request.getDescription()
        );



        Category updatedCategory =
                categoryRepository.save(existingCategory);



        logger.info(
                "Category updated: {}",
                updatedCategory.getCategoryName()
        );



        return categoryMapper.toResponse(updatedCategory);

    }





    // ================= SOFT DELETE =================

    @Override
    @Transactional
    public void deleteCategory(
            Integer categoryId
    ){


        Category category =
                categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );



        category.setActive(false);



        category.setDeletedAt(
                LocalDateTime.now()
        );



        categoryRepository.save(category);



        logger.info(
                "Category soft deleted: {}",
                categoryId
        );

    }





    // ================= RESTORE CATEGORY =================

    @Override
    @Transactional
    public CategoryResponseDTO restoreCategory(
            Integer categoryId
    ){


        Category category =
                categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"
                        )
                );



        category.setActive(true);



        category.setDeletedAt(null);



        Category restoredCategory =
                categoryRepository.save(category);



        logger.info(
                "Category restored: {}",
                categoryId
        );



        return categoryMapper.toResponse(restoredCategory);

    }


}