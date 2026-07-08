package com.inventory_management.service;

import com.inventory_management.dto.request.CustomerRequestDTO;
import com.inventory_management.dto.response.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerResponseDTO createCustomer(CustomerRequestDTO request);

    List<CustomerResponseDTO> getAllCustomers();

    CustomerResponseDTO getCustomerById(Integer customerId);

    CustomerResponseDTO updateCustomer(Integer customerId, CustomerRequestDTO request);

    void deleteCustomer(Integer customerId);

}