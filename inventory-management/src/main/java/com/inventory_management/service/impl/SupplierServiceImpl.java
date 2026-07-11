package com.inventory_management.service.impl;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.entity.Supplier;
import com.inventory_management.mapper.SupplierMapper;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {
    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;


    @Override
    @Transactional
    public SupplierResponseDTO createSupplier(SupplierRequestDTO request) {

        Supplier supplier = supplierMapper.toEntity(request);

        Supplier savedSupplier = supplierRepository.save(supplier);
        logger.info("Supplier created: {}", savedSupplier);
        return supplierMapper.toResponse(savedSupplier);
    }


    @Override
    @Transactional(readOnly = true)
        public Page<SupplierResponseDTO> getAllSuppliers(
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Fetching all suppliers");

        return supplierRepository.findAll(pageable)
            .map(supplierMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponseDTO> searchSuppliers(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
        ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info("Searching suppliers with keyword: {}", keyword);

        return supplierRepository.searchSuppliers(keyword, pageable)
            .map(supplierMapper::toResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public SupplierResponseDTO getSupplierById(Integer supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() ->
                        new RuntimeException("Supplier not found")
                );

        logger.info("Fetching supplier by ID: {}", supplierId);
        return supplierMapper.toResponse(supplier);
    }


    @Override
    @Transactional
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
    @Transactional
    public void deleteSupplier(Integer supplierId) {
        logger.info("Deleting supplier by ID: {}", supplierId);


        if(!supplierRepository.existsById(supplierId)){

            throw new RuntimeException("Supplier not found");

        }


        supplierRepository.deleteById(supplierId);

        logger.info("Supplier deleted: {}", supplierId);

    }

}