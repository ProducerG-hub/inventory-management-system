package com.inventory_management.controller;

import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;
import com.inventory_management.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management APIs")
public class CategoryController {


    private final CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new category", description = "Creates a new category in the inventory")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @Valid @RequestBody CategoryRequestDTO request
    ){

        CategoryResponseDTO response =
                categoryService.createCategory(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }



    @GetMapping
    @PreAuthorize("hasRole('ADMIN','STAFF')")
    @Operation(summary = "Get all categories", description = "Retrieves a paginated list of all categories in the inventory")
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "categoryId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){

        return ResponseEntity.ok(
                categoryService.getAllCategories(page, size, sortBy, sortDir)
        );
    }

        @GetMapping("/search")
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
        @Operation(summary = "Search categories", description = "Searches for categories by keyword with pagination and sorting")
        public ResponseEntity<Page<CategoryResponseDTO>> searchCategories(
                @RequestParam String keyword,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "categoryId") String sortBy,
                @RequestParam(defaultValue = "asc") String sortDir
        ){

                return ResponseEntity.ok(
                        categoryService.searchCategories(keyword, page, size, sortBy, sortDir)
                );
        }

    @GetMapping("/{id}")
        @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(summary = "Get category by ID", description = "Retrieves a category by its ID")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }



    @PutMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        @Operation(summary = "Update category by ID", description = "Updates an existing category by its ID")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryRequestDTO request
    ){

        return ResponseEntity.ok(
                categoryService.updateCategory(id, request)
        );
    }



    @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete category by ID", description = "Deletes a category by its ID")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id
    ){

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

}