package com.inventory_management.service.impl;

import com.inventory_management.dto.request.CustomerRequestDTO;
import com.inventory_management.dto.response.CustomerResponseDTO;
import com.inventory_management.entity.Customer;
import com.inventory_management.mapper.CustomerMapper;
import com.inventory_management.repository.CustomerRepository;
import com.inventory_management.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {

        Customer customer = customerMapper.toEntity(request);

        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer created: {}", savedCustomer.getCustomerName());
        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAllCustomers() {
        logger.info("Fetching all customers");
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDTO getCustomerById(Integer customerId) {
        logger.info("Fetching customer by ID: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found")
                );

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponseDTO updateCustomer(
            Integer customerId,
            CustomerRequestDTO request
    ) {

        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found")
                );

        existingCustomer.setCustomerName(request.getCustomerName());
        existingCustomer.setPhone(request.getPhone());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setStreet(request.getStreet());
        existingCustomer.setDistrict(request.getDistrict());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        logger.info("Customer updated: {}", updatedCustomer.getCustomerName());
        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer customerId) {
        logger.info("Deleting customer by ID: {}", customerId);

        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found");
        }

        customerRepository.deleteById(customerId);
        logger.info("Customer deleted: {}", customerId);
    }

}