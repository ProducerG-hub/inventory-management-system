package com.inventory_management.service;

import com.inventory_management.dto.request.ProductRequestDTO;
import com.inventory_management.dto.response.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO request);

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Integer productId);

    ProductResponseDTO updateProduct(Integer productId, ProductRequestDTO request);

    void deleteProduct(Integer productId);

}