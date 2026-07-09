package com.inventory_management.mapper;

import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.response.SaleItemResponseDTO;
import com.inventory_management.entity.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleItemMapper {

    @Mapping(target = "sale", ignore = true)
    @Mapping(target = "product", ignore = true)
    SaleItem toEntity(SaleItemRequestDTO request);

    @Mapping(source = "sale.saleId", target = "saleId")
    @Mapping(source = "sale.customer.customerName", target = "customerName")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    SaleItemResponseDTO toResponse(SaleItem saleItem);

}