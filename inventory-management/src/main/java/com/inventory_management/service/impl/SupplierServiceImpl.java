package com.inventory_management.service.impl;

import com.inventory_management.dto.request.SupplierRequestDTO;
import com.inventory_management.dto.response.SupplierResponseDTO;
import com.inventory_management.entity.Supplier;
import com.inventory_management.mapper.SupplierMapper;
import com.inventory_management.repository.SupplierRepository;
import com.inventory_management.service.SupplierService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger =
            LoggerFactory.getLogger(SupplierServiceImpl.class);

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    @Override
    @Transactional
    public SupplierResponseDTO createSupplier(
            SupplierRequestDTO request
    ) {

        Supplier supplier = supplierMapper.toEntity(request);

        supplier.setActive(true);
        supplier.setDeletedAt(null);

        Supplier savedSupplier =
                supplierRepository.save(supplier);

        logger.info(
                "Supplier created: {}",
                savedSupplier.getSupplierName()
        );

        return supplierMapper.toResponse(savedSupplier);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponseDTO> getAllSuppliers(
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info(
                "Fetching {} suppliers",
                active ? "active" : "inactive"
        );

        return supplierRepository
                .findByActive(active, pageable)
                .map(supplierMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<SupplierResponseDTO> searchSuppliers(
            String keyword,
            Boolean active,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        logger.info(
                "Searching {} suppliers with keyword: {}",
                active ? "active" : "inactive",
                keyword
        );

        return supplierRepository
                .searchSuppliers(
                        keyword,
                        active,
                        pageable
                )
                .map(supplierMapper::toResponse);

    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponseDTO getSupplierById(
            Integer supplierId
    ) {

        logger.info(
                "Fetching supplier by ID: {}",
                supplierId
        );

        Supplier supplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"
                                )
                        );

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
                                new RuntimeException(
                                        "Supplier not found"
                                )
                        );

        existingSupplier.setSupplierName(
                request.getSupplierName()
        );

        existingSupplier.setCompanyName(
                request.getCompanyName()
        );

        existingSupplier.setPhone(
                request.getPhone()
        );

        existingSupplier.setEmail(
                request.getEmail()
        );

        existingSupplier.setStreet(
                request.getStreet()
        );

        existingSupplier.setDistrict(
                request.getDistrict()
        );

        Supplier updatedSupplier =
                supplierRepository.save(existingSupplier);

        logger.info(
                "Supplier updated: {}",
                updatedSupplier.getSupplierName()
        );

        return supplierMapper.toResponse(updatedSupplier);

    }

    @Override
    @Transactional
    public void deleteSupplier(
            Integer supplierId
    ) {

        logger.info(
                "Soft deleting supplier: {}",
                supplierId
        );

        Supplier supplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"
                                )
                        );

        supplier.setActive(false);

        supplier.setDeletedAt(
                LocalDateTime.now()
        );

        supplierRepository.save(supplier);

        logger.info(
                "Supplier deactivated: {}",
                supplier.getSupplierName()
        );

    }

    @Override
    @Transactional
    public void restoreSupplier(
            Integer supplierId
    ) {

        logger.info(
                "Restoring supplier: {}",
                supplierId
        );

        Supplier supplier =
                supplierRepository.findById(supplierId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Supplier not found"
                                )
                        );

        supplier.setActive(true);

        supplier.setDeletedAt(null);

        supplierRepository.save(supplier);

        logger.info(
                "Supplier restored: {}",
                supplier.getSupplierName()
        );

    }

}