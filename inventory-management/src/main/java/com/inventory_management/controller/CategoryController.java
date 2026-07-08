package com.inventory_management.controller;

import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;
import com.inventory_management.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @RequestBody CategoryRequestDTO request
    ){

        CategoryResponseDTO response =
                categoryService.createCategory(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }



    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }



    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable Integer id
    ){

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }



    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Integer id,
            @RequestBody CategoryRequestDTO request
    ){

        return ResponseEntity.ok(
                categoryService.updateCategory(id, request)
        );
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id
    ){

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }

}