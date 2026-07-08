package com.inventory_management.mapper;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.entity.Supplier;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SupplierMapper {


    Supplier toEntity(SupplierRequestDTO request);


    SupplierResponseDTO toResponse(Supplier supplier);

}