package com.inventory_management.service.impl;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.entity.Supplier;
import com.inventory_management.mapper.SupplierMapper;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.service.SupplierService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {


    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;


    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO request) {

        Supplier supplier = supplierMapper.toEntity(request);

        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.toResponse(savedSupplier);
    }


    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {

        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public SupplierResponseDTO getSupplierById(Integer supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found")
                );

        return supplierMapper.toResponse(supplier);
    }


    @Override
    public SupplierResponseDTO updateSupplier(
            Integer supplierId,
            SupplierRequestDTO request
    ) {

        Supplier existingSupplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException("Supplier not found")
                        );


        existingSupplier.setSupplierName(request.getSupplierName());
        existingSupplier.setCompanyName(request.getCompanyName());
        existingSupplier.setPhone(request.getPhone());
        existingSupplier.setEmail(request.getEmail());
        existingSupplier.setStreet(request.getStreet());
        existingSupplier.setDistrict(request.getDistrict());


        Supplier updatedSupplier =
                supplierRepository.save(existingSupplier);


        return supplierMapper.toResponse(updatedSupplier);
    }


    @Override
    public void deleteSupplier(Integer supplierId) {


        if(!supplierRepository.existsById(supplierId)){

            throw new RuntimeException("Supplier not found");

        }


        supplierRepository.deleteById(supplierId);

    }

}