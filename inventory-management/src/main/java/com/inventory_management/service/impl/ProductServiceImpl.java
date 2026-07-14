package com.inventory_management.service.impl;

import com.inventory_management.dto.request.ProductRequestDTO;
import com.inventory_management.dto.response.ProductResponseDTO;
import com.inventory_management.entity.Category;
import com.inventory_management.entity.Product;
import com.inventory_management.entity.Supplier;
import com.inventory_management.exception.ResourceNotFoundException;
import com.inventory_management.mapper.ProductMapper;
import com.inventory_management.repository.CategoryRepository;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.service.ProductService;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final SupplierRepository supplierRepository;

    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        Product product = productMapper.toEntity(request);
        product.setCategory(getCategoryById(request.getCategoryId()));
        product.setSupplier(getSupplierById(request.getSupplierId()));

        Product savedProduct = productRepository.save(product);
        logger.info("Product created: {}", savedProduct.getProductName());

        return productMapper.toResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> getAllProducts(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }

    @Override
        @Transactional(readOnly = true)
        public Page<ProductResponseDTO> searchProducts(

                String keyword,

                int page,

                int size,

                String sortBy,

                String sortDir

        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return productRepository

                .searchProducts(keyword, pageable)

                .map(productMapper::toResponse);

        }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Integer productId) {
        logger.info("Fetching product by ID: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        return productMapper.toResponse(product);
    }

    @Override
    @Transactional
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
        logger.info("Product updated: {}", updatedProduct.getProductName());

        return productMapper.toResponse(updatedProduct);
    }

    @Override
        @Transactional
        public void deleteProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        product.setIsActive(false);

        productRepository.save(product);
        }

        @Override
        public Page<ProductResponseDTO> getActiveProducts(
                int page,
                int size,
                String sortBy,
                String sortDir
        ){

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository
                .findByIsActiveTrue(pageable)
                .map(productMapper::toResponse);

        }
        @Override
        public Page<ProductResponseDTO> getInactiveProducts(
                int page,
                int size,
                String sortBy,
                String sortDir
        ){

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository
                .findByIsActiveFalse(pageable)
                .map(productMapper::toResponse);

        }
    private Category getCategoryById(Integer categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private Supplier getSupplierById(Integer supplierId) {

        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
    }
@Override
public void restoreProduct(Integer id){

    Product product = productRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Product not found"));

    product.setIsActive(true);

    productRepository.save(product);

}

}