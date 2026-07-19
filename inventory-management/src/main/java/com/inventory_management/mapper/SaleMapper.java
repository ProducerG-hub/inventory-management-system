package com.inventory_management.mapper;

import com.inventory_management.dto.response.SaleResponseDTO;
import com.inventory_management.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = SaleItemMapper.class
)
public interface SaleMapper {

    @Mapping(target = "customerId", source = "customer.customerId")
    @Mapping(target = "customerName", source = "customer.customerName")

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "userFullName", source = "user.fullName")

    @Mapping(target = "items", source = "saleItems")
    SaleResponseDTO toResponse(Sale sale);

}