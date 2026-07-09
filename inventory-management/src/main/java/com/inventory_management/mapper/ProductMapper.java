package com.inventory_management.mapper;

import com.inventory_management.dto.request.ProductRequestDTO;
import com.inventory_management.dto.response.ProductResponseDTO;
import com.inventory_management.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    Product toEntity(ProductRequestDTO request);

    @Mapping(source = "category.categoryId", target = "categoryId")
    @Mapping(source = "category.categoryName", target = "categoryName")
    @Mapping(source = "supplier.supplierId", target = "supplierId")
    @Mapping(source = "supplier.supplierName", target = "supplierName")
    ProductResponseDTO toResponse(Product product);

}