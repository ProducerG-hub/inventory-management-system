package com.inventory_management.service.impl;

import com.inventory_management.dto.request.CustomerRequestDTO;
import com.inventory_management.dto.response.CustomerResponseDTO;
import com.inventory_management.entity.Customer;
import com.inventory_management.mapper.CustomerMapper;
import com.inventory_management.repository.CustomerRepository;
import com.inventory_management.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {

        Customer customer = customerMapper.toEntity(request);

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO getCustomerById(Integer customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found")
                );

        return customerMapper.toResponse(customer);
    }

    @Override
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

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Integer customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new RuntimeException("Customer not found");
        }

        customerRepository.deleteById(customerId);
    }

}