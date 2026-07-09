package com.inventory_management.mapper;

import com.inventory_management.dto.request.SaleRequestDTO;
import com.inventory_management.dto.response.SaleResponseDTO;
import com.inventory_management.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "saleItems", ignore = true)
    Sale toEntity(SaleRequestDTO request);

    @Mapping(source = "customer.customerId", target = "customerId")
    @Mapping(source = "customer.customerName", target = "customerName")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "userFullName")
    SaleResponseDTO toResponse(Sale sale);

}