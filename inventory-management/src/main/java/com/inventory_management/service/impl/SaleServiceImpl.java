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

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;

    private final CustomerRepository customerRepository;

    private final UserRepository userRepository;

    private final SaleMapper saleMapper;

    @Override
    public SaleResponseDTO createSale(SaleRequestDTO request) {

        Sale sale = saleMapper.toEntity(request);
        sale.setCustomer(getCustomerById(request.getCustomerId()));
        sale.setUser(getUserById(request.getUserId()));

        Sale savedSale = saleRepository.save(sale);

        return saleMapper.toResponse(savedSale);
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {

        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SaleResponseDTO getSaleById(Integer saleId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new RuntimeException("Sale not found")
                );

        return saleMapper.toResponse(sale);
    }

    @Override
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

        return saleMapper.toResponse(updatedSale);
    }

    @Override
    public void deleteSale(Integer saleId) {

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