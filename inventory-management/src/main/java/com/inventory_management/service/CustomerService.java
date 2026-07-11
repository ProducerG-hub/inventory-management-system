package com.inventory_management.service;

import com.inventory_management.dto.request.CustomerRequestDTO;
import com.inventory_management.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

        Page<CustomerResponseDTO> getAllCustomers(
            int page,
            int size,
            String sortBy,
            String sortDir
        );

    CustomerResponseDTO getCustomerById(Integer customerId);

    CustomerResponseDTO updateCustomer(Integer customerId, CustomerRequestDTO request);

    void deleteCustomer(Integer customerId);

}