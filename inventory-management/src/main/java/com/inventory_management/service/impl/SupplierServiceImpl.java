package com.inventory_management.service.impl;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.entity.Supplier;
import com.inventory_management.mapper.SupplierMapper;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;


    @Override
    public SupplierResponseDTO createSupplier(SupplierRequestDTO request) {

        Supplier supplier = supplierMapper.toEntity(request);

        Supplier savedSupplier = supplierRepository.save(supplier);
        logger.info("Supplier created: {}", savedSupplier);
        return supplierMapper.toResponse(savedSupplier);
    }


    @Override
    public List<SupplierResponseDTO> getAllSuppliers() {
        logger.info("Fetching all suppliers");


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

        logger.info("Fetching supplier by ID: {}", supplierId);
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


        logger.info("Supplier updated: {}", updatedSupplier);
        return supplierMapper.toResponse(updatedSupplier);
    }


    @Override
    public void deleteSupplier(Integer supplierId) {
        logger.info("Deleting supplier by ID: {}", supplierId);


        if(!supplierRepository.existsById(supplierId)){

            throw new RuntimeException("Supplier not found");

        }


        supplierRepository.deleteById(supplierId);

    }

}