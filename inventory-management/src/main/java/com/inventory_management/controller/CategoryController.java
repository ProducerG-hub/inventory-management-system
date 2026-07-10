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
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management APIs")
public class CategoryController {


    private final CategoryService categoryService;


    @PostMapping
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
        @Operation(summary = "Get all categories", description = "Retrieves a list of all categories in the inventory")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }



    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieves a category by its ID")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }



    @PutMapping("/{id}")
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
    @Operation(summary = "Delete category by ID", description = "Deletes a category by its ID")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id
    ){

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

}