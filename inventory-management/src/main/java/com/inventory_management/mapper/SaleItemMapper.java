package com.inventory_management.mapper;

import com.inventory_management.dto.response.SaleItemResponseDTO;
import com.inventory_management.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "productName", source = "product.productName")
    SaleItemResponseDTO toResponse(SaleItem saleItem);

}