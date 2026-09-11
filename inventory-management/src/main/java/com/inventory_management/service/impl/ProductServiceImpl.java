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
import com.inventory_management.entity.StockMovement;
import com.inventory_management.entity.User;
import com.inventory_management.entity.enums.movementType;
import com.inventory_management.repository.StockMovementRepository;

import lombok.RequiredArgsConstructor;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.audit.AuditEventType;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.service.AuditEventPublisher;
import com.inventory_management.security.CustomUserDetails;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;
    private final StockMovementRepository stockMovementRepository;
    private final AuditEventPublisher auditEventPublisher;

    @Override
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO request){

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();
        }
        else {
            throw new IllegalStateException("Authenticated user not found");
        }

        Product product =
                productMapper.toEntity(request);

        product.setCategory(
                getCategoryById(request.getCategoryId())
        );

        product.setSupplier(
                getSupplierById(request.getSupplierId())
        );

        Product savedProduct =
                productRepository.save(product);

        StockMovement movement =
                new StockMovement();

        movement.setProduct(savedProduct);

        movement.setUser(authUser);

        movement.setQuantity(
                savedProduct.getQuantity()
        );

        movement.setMovementType(
                movementType.IN
        );

        movement.setRemarks(
                "Initial stock added"
        );

        stockMovementRepository.save(movement);

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.CREATE)
                        .entityType(AuditEntityType.PRODUCT)
                        .entityId(savedProduct.getProductId())
                        .description("Product created successfully")
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

        logger.info(
                "Product created: {}",
                savedProduct.getProductName()
        );

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
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();
        } else {
            throw new IllegalStateException("Authenticated user not found");
        }

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found")
                );

        Map<String, Object> oldValues = new HashMap<>();
        Map<String, Object> newValues = new HashMap<>();

        if (!Objects.equals(existingProduct.getProductName(), request.getProductName())) {
            oldValues.put("productName", existingProduct.getProductName());
            newValues.put("productName", request.getProductName());
        }

        if (existingProduct.getBuyingPrice().compareTo(request.getBuyingPrice()) != 0) {
            oldValues.put("buyingPrice", existingProduct.getBuyingPrice());
            newValues.put("buyingPrice", request.getBuyingPrice());
        }

        if (existingProduct.getSellingPrice().compareTo(request.getSellingPrice()) != 0) {
            oldValues.put("sellingPrice", existingProduct.getSellingPrice());
            newValues.put("sellingPrice", request.getSellingPrice());
        }

        if (!Objects.equals(existingProduct.getQuantity(), request.getQuantity())) {
            oldValues.put("quantity", existingProduct.getQuantity());
            newValues.put("quantity", request.getQuantity());
        }

        if (!Objects.equals(existingProduct.getIsActive(), request.getIsActive())) {
            oldValues.put("isActive", existingProduct.getIsActive());
            newValues.put("isActive", request.getIsActive());
        }

        Integer oldCategoryId = existingProduct.getCategory() != null
                ? existingProduct.getCategory().getCategoryId()
                : null;

        Integer newCategoryId = request.getCategoryId();

        if (!Objects.equals(oldCategoryId, newCategoryId)) {
            oldValues.put("categoryId", oldCategoryId);
            newValues.put("categoryId", newCategoryId);
        }

        Integer oldSupplierId = existingProduct.getSupplier() != null
                ? existingProduct.getSupplier().getSupplierId()
                : null;

        Integer newSupplierId = request.getSupplierId();

        if (!Objects.equals(oldSupplierId, newSupplierId)) {
            oldValues.put("supplierId", oldSupplierId);
            newValues.put("supplierId", newSupplierId);
        }

        Integer previousQuantity = existingProduct.getQuantity();
        Integer updatedQuantity = request.getQuantity();

        if (oldValues.isEmpty()) {
            return productMapper.toResponse(existingProduct);
        }


        existingProduct.setProductName(request.getProductName());
        existingProduct.setBuyingPrice(request.getBuyingPrice());
        existingProduct.setSellingPrice(request.getSellingPrice());
        existingProduct.setQuantity(updatedQuantity);
        existingProduct.setIsActive(request.getIsActive());
        existingProduct.setCategory(getCategoryById(request.getCategoryId()));
        existingProduct.setSupplier(getSupplierById(request.getSupplierId()));

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated: {}", updatedProduct.getProductName());

        //recording the stock movement if the quantity has changed
        if (!previousQuantity.equals(updatedQuantity)) {
            StockMovement movement = new StockMovement();
            movement.setProduct(updatedProduct);
            movement.setUser(authUser);
            movement.setQuantity(Math.abs(updatedQuantity - previousQuantity));
            movement.setMovementType(
                    updatedQuantity > previousQuantity
                            ? movementType.IN
                            : movementType.OUT
            );
            if(movement.getMovementType() == movementType.IN) {
                movement.setRemarks("Stock increased");
            } else {
                movement.setRemarks("Stock decreased");
            }
            stockMovementRepository.save(movement);
        }

        String oldValuesJson;
        String newValuesJson;

        try {
            oldValuesJson = objectMapper.writeValueAsString(oldValues);
            newValuesJson = objectMapper.writeValueAsString(newValues);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize audit values", e);
        }
        
        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.UPDATE)
                        .entityType(AuditEntityType.PRODUCT)
                        .entityId(updatedProduct.getProductId())
                        .description("Product updated successfully")
                        .oldValues(oldValuesJson)
                        .newValues(newValuesJson)
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

        return productMapper.toResponse(updatedProduct);
    }

    @Override
        @Transactional
        public void deleteProduct(Integer productId) {
        
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User authUser = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            authUser = userDetails.getUser();
        }
         else {
            throw new IllegalStateException("Authenticated user not found");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found")
                );

        product.setIsActive(false);

        productRepository.save(product);

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.DEACTIVATE)
                        .entityType(AuditEntityType.PRODUCT)
                        .entityId(product.getProductId())
                        .description("Product deactivated successfully")
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

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

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        User authUser = null;

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            authUser = userDetails.getUser();
        }
         else {
            throw new IllegalStateException("Authenticated user not found");
        }

    Product product = productRepository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Product not found"));

    product.setIsActive(true);

    productRepository.save(product);

    auditEventPublisher.publish(
            AuditEvent.builder()
                    .user(authUser)
                    .action(AuditAction.RESTORE)
                    .entityType(AuditEntityType.PRODUCT)
                    .entityId(product.getProductId())
                    .description("Product restored successfully")
                    .eventType(AuditEventType.BUSINESS)
                    .build()
    );
}

}