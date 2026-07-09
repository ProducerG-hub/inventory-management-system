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

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl implements SaleItemService {

    private final SaleItemRepository saleItemRepository;

    private final SaleRepository saleRepository;

    private final ProductRepository productRepository;

    private final SaleItemMapper saleItemMapper;

    @Override
    public SaleItemResponseDTO createSaleItem(SaleItemRequestDTO request) {

        SaleItem saleItem = saleItemMapper.toEntity(request);
        saleItem.setSale(getSaleById(request.getSaleId()));
        saleItem.setProduct(getProductById(request.getProductId()));

        SaleItem savedSaleItem = saleItemRepository.save(saleItem);

        return saleItemMapper.toResponse(savedSaleItem);
    }

    @Override
    public List<SaleItemResponseDTO> getAllSaleItems() {

        return saleItemRepository.findAll()
                .stream()
                .map(saleItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SaleItemResponseDTO getSaleItemById(Integer saleItemId) {

        SaleItem saleItem = saleItemRepository.findById(saleItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Sale Item not found")
                );

        return saleItemMapper.toResponse(saleItem);
    }

    @Override
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

        return saleItemMapper.toResponse(updatedSaleItem);
    }

    @Override
    public void deleteSaleItem(Integer saleItemId) {

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