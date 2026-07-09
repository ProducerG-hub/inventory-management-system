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

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final StockMovementMapper stockMovementMapper;

    @Override
    public StockMovementResponseDTO createStockMovement(StockMovementRequestDTO request) {

        StockMovement stockMovement = stockMovementMapper.toEntity(request);
        stockMovement.setProduct(getProductById(request.getProductId()));
        stockMovement.setUser(getUserById(request.getUserId()));

        StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);

        return stockMovementMapper.toResponse(savedStockMovement);
    }

    @Override
    public List<StockMovementResponseDTO> getAllStockMovements() {

        return stockMovementRepository.findAll()
                .stream()
                .map(stockMovementMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StockMovementResponseDTO getStockMovementById(Integer movementId) {

        StockMovement stockMovement = stockMovementRepository.findById(movementId)
                .orElseThrow(() ->
                        new RuntimeException("Stock Movement not found")
                );

        return stockMovementMapper.toResponse(stockMovement);
    }

    @Override
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

        return stockMovementMapper.toResponse(updatedStockMovement);
    }

    @Override
    public void deleteStockMovement(Integer movementId) {

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