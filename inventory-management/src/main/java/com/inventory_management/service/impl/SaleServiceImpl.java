package com.inventory_management.service.impl;

import com.inventory_management.dto.request.CheckoutRequestDTO;
import com.inventory_management.dto.request.SaleItemRequestDTO;
import com.inventory_management.dto.request.SaleRequestDTO;
import com.inventory_management.dto.response.CheckoutResponseDTO;
import com.inventory_management.dto.response.ReceiptResponseDTO;
import com.inventory_management.dto.response.SaleResponseDTO;
import com.inventory_management.entity.Customer;
import com.inventory_management.entity.Product;
import com.inventory_management.entity.Sale;
import com.inventory_management.entity.SaleItem;
import com.inventory_management.entity.User;
import com.inventory_management.mapper.SaleItemMapper;
import com.inventory_management.mapper.SaleMapper;
import com.inventory_management.repository.CustomerRepository;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.SaleItemRepository;
import com.inventory_management.repository.SaleRepository;
import com.inventory_management.repository.UserRepository;
import com.inventory_management.service.SaleService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    private final UserRepository userRepository;
    private final SaleMapper saleMapper;
    private final SaleItemMapper saleItemMapper;

    @Override
    public SaleResponseDTO createSale(SaleRequestDTO request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Sale sale = new Sale();

        sale.setCustomer(customer);
        sale.setUser(user);
        sale.setTotalAmount(BigDecimal.ZERO);

        Sale savedSale = saleRepository.save(sale);

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (SaleItemRequestDTO itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product not found"));

            if (!product.getIsActive()) {
                throw new RuntimeException(
                        product.getProductName() + " is inactive"
                );
            }

            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for " + product.getProductName()
                );
            }

            BigDecimal unitPrice = product.getSellingPrice();

            BigDecimal subtotal =
                    unitPrice.multiply(
                            BigDecimal.valueOf(itemRequest.getQuantity())
                    );

            SaleItem saleItem = new SaleItem();

            saleItem.setSale(savedSale);
            saleItem.setProduct(product);
            saleItem.setUnitPrice(unitPrice);
            saleItem.setQuantity(itemRequest.getQuantity());
            saleItem.setSubtotal(subtotal);

            saleItemRepository.save(saleItem);

            product.setQuantity(
                    product.getQuantity() - itemRequest.getQuantity()
            );

            productRepository.save(product);

            grandTotal = grandTotal.add(subtotal);
        }

        savedSale.setTotalAmount(grandTotal);

        saleRepository.save(savedSale);

        logger.info("Sale completed successfully. Total = {}", grandTotal);

        return saleMapper.toResponse(savedSale);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SaleResponseDTO> getAllSales(
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

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
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return saleRepository.searchSales(keyword, pageable)
                .map(saleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public SaleResponseDTO getSaleById(Integer saleId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new RuntimeException("Sale not found"));

        return saleMapper.toResponse(sale);
    }

    @Override
    public SaleResponseDTO updateSale(
            Integer saleId,
            SaleRequestDTO request
    ) {

        throw new UnsupportedOperationException(
                "Updating completed sales is not allowed."
        );
    }

    @Override
    public void deleteSale(Integer saleId) {

        throw new UnsupportedOperationException(
                "Deleting completed sales is not allowed."
        );
    }

    @Override
    public CheckoutResponseDTO checkout(CheckoutRequestDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkout'");
    }

    @Override
    @Transactional(readOnly = true)
    public ReceiptResponseDTO getReceipt(Integer saleId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() ->
                        new RuntimeException("Sale not found"));

        ReceiptResponseDTO receipt = new ReceiptResponseDTO();

        receipt.setSaleId(sale.getSaleId());

        receipt.setSaleDate(sale.getSaleDate());

        receipt.setCustomerName(
                sale.getCustomer().getCustomerName()
        );

        receipt.setCashier(
                sale.getUser().getFullName()
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

}