package com.inventory_management.controller;
import com.inventory_management.dto.request.CategoryRequestDTO;
import com.inventory_management.dto.response.CategoryResponseDTO;
import com.inventory_management.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(
        name = "Categories",
        description = "Category management APIs"
)
public class CategoryController {



    private final CategoryService categoryService;

    // ================= CREATE CATEGORY =================

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Create a new category",
            description = "Creates a new category"
    )
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

    // ================= GET CATEGORIES =================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(
            summary = "Get categories by status",
            description = "Retrieves active or inactive categories with pagination"
    )
    public ResponseEntity<Page<CategoryResponseDTO>> getAllCategories(
            @RequestParam(defaultValue = "true")
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "categoryId")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir
    ){


        return ResponseEntity.ok(
                categoryService.getAllCategories(
                        active,
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );

    }

    // ================= SEARCH CATEGORY =================

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(
            summary = "Search categories",
            description = "Search categories by keyword and status"
    )
    public ResponseEntity<Page<CategoryResponseDTO>> searchCategories(
            @RequestParam String keyword,

            @RequestParam(defaultValue = "true")
            Boolean active,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "categoryId")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String sortDir
    ){


        return ResponseEntity.ok(
                categoryService.searchCategories(
                        keyword,
                        active,
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );

    }

    // ================= GET CATEGORY BY ID =================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    @Operation(
            summary = "Get category by ID",
            description = "Retrieves category details"
    )
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable Integer id
    ){


        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );

    }


    // ================= UPDATE CATEGORY =================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Update category",
            description = "Updates category information"
    )
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Integer id,

            @Valid @RequestBody CategoryRequestDTO request
    ){


        return ResponseEntity.ok(
                categoryService.updateCategory(
                        id,
                        request
                )
        );

    }

    // ================= SOFT DELETE =================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete category",
            description = "Soft deletes category"
    )
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id
    ){


        categoryService.deleteCategory(id);


        return ResponseEntity.noContent()
                .build();

    }

    // ================= RESTORE CATEGORY =================

    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Restore category",
            description = "Restores deleted category"
    )
    public ResponseEntity<CategoryResponseDTO> restoreCategory(
            @PathVariable Integer id
    ){


        return ResponseEntity.ok(
                categoryService.restoreCategory(id)
        );

    }


}