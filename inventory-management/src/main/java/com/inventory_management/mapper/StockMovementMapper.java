package com.inventory_management.mapper;

import com.inventory_management.dto.request.StockMovementRequestDTO;
import com.inventory_management.dto.response.StockMovementResponseDTO;
import com.inventory_management.entity.StockMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMovementMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "user", ignore = true)
    StockMovement toEntity(StockMovementRequestDTO request);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.fullName", target = "userFullName")
    StockMovementResponseDTO toResponse(StockMovement stockMovement);

}