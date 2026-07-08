package com.inventory_management.mapper;

import com.inventory_management.dto.request.CustomerRequestDTO;
import com.inventory_management.dto.response.CustomerResponseDTO;
import com.inventory_management.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO request);

    CustomerResponseDTO toResponse(Customer customer);

}