package com.inventory_management.service.impl;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;
import com.inventory_management.entity.Product;
import com.inventory_management.entity.StockMovement;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.StockMovementMapper;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.StockMovementRepository;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.StockMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementServiceImpl implements StockMovementService {
    private static final Logger logger = LoggerFactory.getLogger(StockMovementServiceImpl.class);
    private final StockMovementRepository stockMovementRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final StockMovementMapper stockMovementMapper;

    @Override
    @Transactional
    public StockMovementResponseDTO createStockMovement(StockMovementRequestDTO request) {

        StockMovement stockMovement = stockMovementMapper.toEntity(request);
        stockMovement.setProduct(getProductById(request.getProductId()));
        stockMovement.setUser(getUserById(request.getUserId()));

        StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);
        logger.info("Stock movement created: {}", savedStockMovement);
        return stockMovementMapper.toResponse(savedStockMovement);
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

    @Override
    @Transactional
    public StockMovementResponseDTO updateStockMovement(
            Integer movementId,
            StockMovementRequestDTO request
    ) {

        StockMovement existingStockMovement = stockMovementRepository.findById(movementId)
                .orElseThrow(() ->
                        new RuntimeException("Stock Movement not found")
                );

        existingStockMovement.setQuantity(request.getQuantity());
        existingStockMovement.setMovementType(request.getMovementType());
        existingStockMovement.setRemarks(request.getRemarks());
        existingStockMovement.setProduct(getProductById(request.getProductId()));
        existingStockMovement.setUser(getUserById(request.getUserId()));

        StockMovement updatedStockMovement = stockMovementRepository.save(existingStockMovement);
        logger.info("Stock movement updated: {}", updatedStockMovement);
        return stockMovementMapper.toResponse(updatedStockMovement);
    }

    @Override
    @Transactional
    public void deleteStockMovement(Integer movementId) {
        logger.info("Deleting stock movement by ID: {}", movementId);

        if (!stockMovementRepository.existsById(movementId)) {
            throw new RuntimeException("Stock Movement not found");
        }

        stockMovementRepository.deleteById(movementId);
    }

    private Product getProductById(Integer productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private User getUserById(Integer userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}