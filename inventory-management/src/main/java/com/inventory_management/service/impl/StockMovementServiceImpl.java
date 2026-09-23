package com.inventory_management.service.impl;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;
import com.inventory_management.entity.Product;
import com.inventory_management.entity.StockMovement;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.StockMovementMapper;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.StockMovementRepository;
import com.inventory_management.service.StockMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import com.inventory_management.dto.response.StockMovementStatsDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.audit.AuditEventType;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.service.AuditEventPublisher;
import com.inventory_management.security.CustomUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementServiceImpl implements StockMovementService {
    private static final Logger logger = LoggerFactory.getLogger(StockMovementServiceImpl.class);
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;
    private final StockMovementMapper stockMovementMapper;
    private final AuditEventPublisher auditEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public StockMovementResponseDTO createStockMovement(
            StockMovementRequestDTO request
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();

        } else {
            throw new IllegalStateException(
                    "Authenticated user not found"
            );
        }

        Product product =
                getProductById(request.getProductId());

        StockMovement stockMovement =
                stockMovementMapper.toEntity(request);

        stockMovement.setProduct(product);
        stockMovement.setUser(authUser);

        StockMovement savedStockMovement =
                stockMovementRepository.save(stockMovement);

        // ============================
        // CREATE STOCK MOVEMENT AUDIT
        // ============================

        Map<String, Object> newValues =
                new HashMap<>();

        newValues.put(
                "productId",
                product.getProductId()
        );

        newValues.put(
                "movementType",
                savedStockMovement.getMovementType()
        );

        newValues.put(
                "quantity",
                savedStockMovement.getQuantity()
        );

        newValues.put(
                "remarks",
                savedStockMovement.getRemarks()
        );

        String newValuesJson;

        try {

            newValuesJson =
                    objectMapper.writeValueAsString(newValues);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    "Error converting stock movement audit values to JSON",
                    e
            );
        }

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.CREATE)
                        .entityType(AuditEntityType.STOCK_MOVEMENT)
                        .entityId(savedStockMovement.getMovementId())
                        .description(
                                "Stock movement created successfully"
                        )
                        .newValues(newValuesJson)
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

        logger.info(
                "Stock movement created: {}",
                savedStockMovement
        );

        return stockMovementMapper.toResponse(savedStockMovement);
    }

    @Override
    @Transactional(readOnly = true)
    public StockMovementStatsDTO getStats(){

        long total = stockMovementRepository.count();
        long totalIn = stockMovementRepository.countStockIn();
        long totalOut = stockMovementRepository.countStockOut();
        long today = stockMovementRepository.countTodayMovements();
        return new StockMovementStatsDTO(
                total,
                totalIn,
                totalOut,
                today
        );
    }

    @Override
    @Transactional(readOnly = true)
        public Page<StockMovementResponseDTO> getAllStockMovements(
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Fetching all stock movements");
        return stockMovementRepository.findAll(pageable)
            .map(stockMovementMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public StockMovementResponseDTO getStockMovementById(Integer movementId) {
        logger.info("Fetching stock movement by ID: {}", movementId);

        StockMovement stockMovement = stockMovementRepository.findById(movementId)
                .orElseThrow(() ->
                        new RuntimeException("Stock Movement not found")
                );

        return stockMovementMapper.toResponse(stockMovement);
    }

        private Product getProductById(Integer productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StockMovementResponseDTO> searchStockMovements(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Searching stock movements with keyword: {}", keyword);

        return stockMovementRepository.searchStockMovements(keyword, pageable)
                .map(stockMovementMapper::toResponse);
    }

}