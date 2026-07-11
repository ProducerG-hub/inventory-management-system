package com.inventory_management.service;

import com.inventory_management.dto.request.ProductRequestDTO;
import com.inventory_management.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    Page<ProductResponseDTO> getAllProducts(
        int page,
        int size,
        String sortBy,
        String sortDir
);

    ProductResponseDTO getProductById(Integer productId);

    ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO request);

    void deleteProduct(Integer productId);

}