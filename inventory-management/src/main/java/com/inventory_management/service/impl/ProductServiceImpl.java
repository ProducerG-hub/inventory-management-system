package com.inventory_management.service.impl;

import com.inventory_management.dto.request.ProductRequestDTO;
import com.inventory_management.dto.response.ProductResponseDTO;
import com.inventory_management.entity.Category;
import com.inventory_management.entity.Product;
import com.inventory_management.entity.Supplier;
import com.inventory_management.mapper.ProductMapper;
import com.inventory_management.repository.CategoryRepository;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final SupplierRepository supplierRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        Product product = productMapper.toEntity(request);
        product.setCategory(getCategoryById(request.getCategoryId()));
        product.setSupplier(getSupplierById(request.getSupplierId()));

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDTO updateProduct(
            Integer productId,
            ProductRequestDTO request
    ) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        existingProduct.setProductName(request.getProductName());
        existingProduct.setBuyingPrice(request.getBuyingPrice());
        existingProduct.setSellingPrice(request.getSellingPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setIsActive(request.getIsActive());
        existingProduct.setCategory(getCategoryById(request.getCategoryId()));
        existingProduct.setSupplier(getSupplierById(request.getSupplierId()));

        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Integer productId) {

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(productId);
    }

    private Category getCategoryById(Integer categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private Supplier getSupplierById(Integer supplierId) {

        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

}