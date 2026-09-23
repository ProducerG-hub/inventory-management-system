package com.inventory_management.service.impl;

import com.inventory_management.dto.request.*;
import com.inventory_management.dto.response.*;
import com.inventory_management.entity.*;
import com.inventory_management.entity.enums.movementType;
import com.inventory_management.mapper.SaleItemMapper;
import com.inventory_management.mapper.SaleMapper;
import com.inventory_management.repository.*;
import com.inventory_management.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

import com.inventory_management.audit.AuditAction;
import com.inventory_management.audit.AuditEntityType;
import com.inventory_management.event.AuditEvent;
import com.inventory_management.service.AuditEventPublisher;
import com.inventory_management.audit.AuditEventType;
import com.inventory_management.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SaleServiceImpl implements SaleService {

    private static final Logger logger =
            LoggerFactory.getLogger(SaleServiceImpl.class);

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final StockMovementRepository stockMovementRepository;
    private final SaleMapper saleMapper;
    private final SaleItemMapper saleItemMapper;
    private final AuditEventPublisher auditEventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public SaleResponseDTO createSale(SaleRequestDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User authUser = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {

            authUser = userDetails.getUser();
        } else {
            throw new IllegalStateException("Authenticated user not found");
        }


        Customer customer =
                customerRepository.findById(request.getCustomerId())
                        .orElseThrow(() ->
                                new RuntimeException("Customer not found")
                        );

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setUser(authUser);
        sale.setTotalAmount(BigDecimal.ZERO);
        Sale savedSale = saleRepository.save(sale);
        BigDecimal grandTotal =
                BigDecimal.ZERO;

        for(SaleItemRequestDTO itemRequest : request.getItems()){

            Product product =
                    productRepository.findById(
                                    itemRequest.getProductId()
                            )
                            .orElseThrow(() ->
                                    new RuntimeException("Product not found")
                            );

            if(!product.getIsActive()){

                throw new RuntimeException(
                        product.getProductName()
                                +" is inactive"
                );

            }

            if(product.getQuantity()
                    <
                    itemRequest.getQuantity()){
                throw new RuntimeException(
                        "Not enough stock for "
                                +
                                product.getProductName()
                );

            }

            BigDecimal unitPrice = product.getSellingPrice();

            BigDecimal costPrice = product.getBuyingPrice();

            BigDecimal subtotal =
                    unitPrice.multiply(
                            BigDecimal.valueOf(
                                    itemRequest.getQuantity()
                            )
                    );

            SaleItem saleItem = new SaleItem();
            saleItem.setSale(savedSale);
            saleItem.setProduct(product);
            saleItem.setUnitPrice(unitPrice);
            saleItem.setCostPrice(costPrice);
            saleItem.setQuantity(itemRequest.getQuantity());
            saleItem.setSubtotal(subtotal);
            saleItemRepository.save(saleItem);
            product.setQuantity(
                    product.getQuantity()
                            -
                            itemRequest.getQuantity()
            );

            productRepository.save(product);

            // ============================
            // CREATE STOCK MOVEMENT OUT
            // ============================

            StockMovement movement =
                    new StockMovement();

            movement.setProduct(product);
            movement.setUser(authUser);
            movement.setQuantity(
                    itemRequest.getQuantity()
            );

            movement.setMovementType(
                    movementType.OUT
            );

            movement.setRemarks(
                    "Sale transaction"
            );

            stockMovementRepository.save(movement);

            grandTotal = grandTotal.add(subtotal);
        }

        savedSale.setTotalAmount(grandTotal);
        saleRepository.save(savedSale);
        Map<String, Object> newValues = new HashMap<>();

        newValues.put(
                "customerId",
                customer.getCustomerId()
        );

        newValues.put(
                "itemCount",
                request.getItems().size()
        );

        newValues.put(
                "totalAmount",
                grandTotal
        );

        String newValuesJson;

        try {
            newValuesJson =
                    objectMapper.writeValueAsString(newValues);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Error converting sale audit values to JSON",
                    e
            );
        }

        auditEventPublisher.publish(
                AuditEvent.builder()
                        .user(authUser)
                        .action(AuditAction.CREATE)
                        .entityType(AuditEntityType.SALE)
                        .entityId(savedSale.getSaleId())
                        .description("Sale created successfully")
                        .newValues(newValuesJson)
                        .eventType(AuditEventType.BUSINESS)
                        .build()
        );

        logger.info(
                "Sale completed successfully Total={}",
                grandTotal
        );

        return saleMapper.toResponse(savedSale);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDTO> getAllSales(
            int page,
            int size,
            String sortBy,
            String sortDir
    ){

        Sort sort =
                sortDir.equalsIgnoreCase("desc")
                        ?
                        Sort.by(sortBy).descending()
                        :
                        Sort.by(sortBy).ascending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        return saleRepository.findAll(pageable)
                .map(saleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDTO> searchSales(
            String keyword,
            int page,
            int size,
            String sortBy,
            String sortDir
    ){

        Sort sort =
                sortDir.equalsIgnoreCase("desc")
                        ?
                        Sort.by(sortBy).descending()
                        :
                        Sort.by(sortBy).ascending();



        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        return saleRepository
                .searchSales(keyword,pageable)
                .map(saleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptResponseDTO getReceipt(Integer saleId){

        Sale sale =
                saleRepository.findById(saleId)
                        .orElseThrow(() ->
                                new RuntimeException("Sale not found")
                        );

        ReceiptResponseDTO receipt =
                new ReceiptResponseDTO();

        receipt.setSaleId(
                sale.getSaleId()
        );

        receipt.setSaleDate(
                sale.getSaleDate()
        );

        receipt.setCustomerName(
                sale.getCustomer()
                        .getCustomerName()
        );

        receipt.setCashier(
                sale.getUser()
                        .getFullName()
        );

        receipt.setTotalAmount(
                sale.getTotalAmount()
        );

        receipt.setItems(
                sale.getSaleItems()
                        .stream()
                        .map(saleItemMapper::toResponse)
                        .toList()
        );

        return receipt;
    }

    @Override
    public SaleResponseDTO getSaleById(Integer id){

        Sale sale =
                saleRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Sale not found")
                        );

        return saleMapper.toResponse(sale);

    }

    @Override
    public SaleResponseDTO updateSale(
            Integer id,
            SaleRequestDTO request
    ){

        throw new UnsupportedOperationException(
                "Updating completed sales is not allowed"
        );

    }

    @Override
    public void deleteSale(Integer id){

        throw new UnsupportedOperationException(
                "Deleting completed sales is not allowed"
        );

    }

    @Override
    public CheckoutResponseDTO checkout(
            CheckoutRequestDTO request
    ){
        throw new UnsupportedOperationException(
                "Not implemented"
        );

    }

}