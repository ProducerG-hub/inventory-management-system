package com.inventory_management.service.impl;

import com.inventory_management.dto.response.*;
import com.inventory_management.entity.Sale;
import com.inventory_management.repository.CustomerRepository;
import com.inventory_management.repository.ProductRepository;
import com.inventory_management.repository.SaleItemRepository;
import com.inventory_management.repository.SaleRepository;
import com.inventory_management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    public ReportSummaryDTO getSummary() {

        return ReportSummaryDTO.builder()
                .totalSales(
                        saleRepository.getTotalSales()
                )
                .totalRevenue(
                        saleRepository.getTotalRevenue()
                )
                .totalProfit(
                        saleItemRepository.getTotalProfit()
                )
                .lowStockProducts(
                        productRepository.countByQuantityLessThanEqual(10)
                )
                .build();

    }
@Override
@Transactional(readOnly = true)
public List<SalesReportDTO> getSalesReport(

        LocalDate startDate,

        LocalDate endDate

){

    List<Sale> sales;


    if(startDate != null && endDate != null){

        sales =
        saleRepository
        .findSalesBetweenDates(
                startDate.atStartOfDay(),
                endDate.plusDays(1)
                       .atStartOfDay()
        );


    }
    else{

        sales =
        saleRepository.findAll();

    }



    return sales.stream()

            .map(sale -> SalesReportDTO.builder()

                    .saleId(
                        sale.getSaleId()
                    )

                    .customerName(
                        sale.getCustomer()
                            .getCustomerName()
                    )

                    .cashierName(
                        sale.getUser()
                            .getFullName()
                    )

                    .totalItems(
                        sale.getSaleItems()
                            .size()
                    )

                    .totalAmount(
                        sale.getTotalAmount()
                    )

                    .saleDate(
                        sale.getSaleDate()
                    )

                    .build()
            )

            .toList();

}

    @Override
    public List<SalesTrendDTO> getSalesTrend() {

        return saleRepository
                .getSalesTrend()
                .stream()
                .map(row -> SalesTrendDTO.builder()

                        .date(
                                ((Date) row[0])
                                        .toLocalDate()
                        )

                        .totalOrders(
                                ((Number) row[1])
                                        .longValue()
                        )

                        .totalRevenue(
                                (BigDecimal) row[2]
                        )

                        .build()

                )
                .toList();

    }

    @Override
    public List<InventoryReportDTO> getInventoryReport() {

        return productRepository.getInventoryReport();

    }

    @Override
    public List<CustomerReportDTO> getCustomerReport() {

        return customerRepository.getCustomerReport();

    }

    @Override
    public List<ProfitReportDTO> getProfitReport() {

        return saleItemRepository.getProfitReport();

    }

    @Override
    public List<TopSellingProductDTO> getTopSellingProducts() {

        return saleItemRepository
                .getTopSellingProducts()
                .stream()
                .map(row -> TopSellingProductDTO.builder()

                        .productId(
                                (Integer) row[0]
                        )

                        .productName(
                                (String) row[1]
                        )

                        .totalQuantitySold(
                                ((Number) row[2])
                                        .longValue()
                        )

                        .totalRevenue(
                                (BigDecimal) row[3]
                        )

                        .totalOrders(
                                ((Number) row[4])
                                        .longValue()
                        )

                        .build()
                )
                .toList();

    }

}