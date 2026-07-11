package com.inventory_management.service.impl;

import com.inventory_management.dto.request.SaleRequestDTO;
import com.inventory_management.dto.response.SaleResponseDTO;
import com.inventory_management.entity.Customer;
import com.inventory_management.entity.Sale;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.SaleMapper;
import com.inventory_management.repository.CustomerRepository;
import com.inventory_management.repository.SaleRepository;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.SaleService;
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
public class SaleServiceImpl implements SaleService {
    private static final Logger logger = LoggerFactory.getLogger(SaleServiceImpl.class);
    private final SaleRepository saleRepository;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO request) {

        Sale sale = saleMapper.toEntity(request);
        sale.setCustomer(getCustomerById(request.getCustomerId()));
        sale.setUser(getUserById(request.getUserId()));

        Sale savedSale = saleRepository.save(sale);
        logger.info("Sale created: {}", savedSale);
        return saleMapper.toResponse(savedSale);
    }

    @Override
    @Transactional(readOnly = true)
        public Page<SaleResponseDTO> getAllSales(
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Fetching all sales");
        return saleRepository.findAll(pageable)
            .map(saleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDTO getSaleById(Integer saleId) {
        logger.info("Fetching sale by ID: {}", saleId);
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new RuntimeException("Sale not found")
                );

        return saleMapper.toResponse(sale);
    }

    @Override
    @Transactional
    public SaleResponseDTO updateSale(
            Integer saleId,
            SaleRequestDTO request
    ) {

        Sale existingSale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new RuntimeException("Sale not found")
                );

        existingSale.setTotalAmount(request.getTotalAmount());
        existingSale.setCustomer(getCustomerById(request.getCustomerId()));
        existingSale.setUser(getUserById(request.getUserId()));

        Sale updatedSale = saleRepository.save(existingSale);
        logger.info("Sale updated: {}", updatedSale);
        return saleMapper.toResponse(updatedSale);
    }

    @Override
    @Transactional
    public void deleteSale(Integer saleId) {
        logger.info("Deleting sale by ID: {}", saleId);


        if (!saleRepository.existsById(saleId)) {
            throw new RuntimeException("Sale not found");
        }

        saleRepository.deleteById(saleId);
    }

    private Customer getCustomerById(Integer customerId) {

        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    private User getUserById(Integer userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}