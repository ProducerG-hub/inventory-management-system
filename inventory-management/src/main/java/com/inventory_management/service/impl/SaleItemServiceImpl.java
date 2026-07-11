package com.inventory_management.service.impl;

import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.response.SaleItemResponseDTO;
import com.inventory_management.entity.Product;
import com.inventory_management.entity.Sale;
import com.inventory_management.entity.SaleItem;
import com.inventory_management.mapper.SaleItemMapper;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.SaleItemRepository;
import com.inventory_management.repository.SaleRepository;
import com.inventory_management.service.SaleItemService;
import com.inventory_management.exception.ResourceNotFoundException;
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
public class SaleItemServiceImpl implements SaleItemService {
    private static final Logger logger = LoggerFactory.getLogger(SaleItemServiceImpl.class);

    private final SaleItemRepository saleItemRepository;

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    private final SaleItemMapper saleItemMapper;

    @Override
    @Transactional
    public SaleItemResponseDTO createSaleItem(SaleItemRequestDTO request) {

        SaleItem saleItem = saleItemMapper.toEntity(request);
        saleItem.setSale(getSaleById(request.getSaleId()));
        saleItem.setProduct(getProductById(request.getProductId()));

        SaleItem savedSaleItem = saleItemRepository.save(saleItem);
        logger.info("Sale item created: {}", savedSaleItem);
        return saleItemMapper.toResponse(savedSaleItem);
    }

    @Override
    @Transactional(readOnly = true)
        public Page<SaleItemResponseDTO> getAllSaleItems(
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Fetching all sale items");
        return saleItemRepository.findAll(pageable)
            .map(saleItemMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleItemResponseDTO getSaleItemById(Integer saleItemId) {
        logger.info("Fetching sale item by ID: {}", saleItemId);
        SaleItem saleItem = saleItemRepository.findById(saleItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sale Item not found")
                );

        return saleItemMapper.toResponse(saleItem);
    }

    @Override
    @Transactional
    public SaleItemResponseDTO updateSaleItem(
            Integer saleItemId,
            SaleItemRequestDTO request
    ) {

        SaleItem existingSaleItem = saleItemRepository.findById(saleItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sale Item not found")
                );

        existingSaleItem.setUnitPrice(request.getUnitPrice());
        existingSaleItem.setQuantity(request.getQuantity());
        existingSaleItem.setSubtotal(request.getSubtotal());
        existingSaleItem.setSale(getSaleById(request.getSaleId()));
        existingSaleItem.setProduct(getProductById(request.getProductId()));

        SaleItem updatedSaleItem = saleItemRepository.save(existingSaleItem);
        logger.info("Sale item updated: {}", saleItemId);
        return saleItemMapper.toResponse(updatedSaleItem);
    }

    @Override
    @Transactional
    public void deleteSaleItem(Integer saleItemId) {
        logger.info("Deleting sale item by ID: {}", saleItemId);

        if (!saleItemRepository.existsById(saleItemId)) {
            throw new ResourceNotFoundException("Sale Item not found");
        }

        saleItemRepository.deleteById(saleItemId);
    }

    private Sale getSaleById(Integer saleId) {

        return saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }

    private Product getProductById(Integer productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

}