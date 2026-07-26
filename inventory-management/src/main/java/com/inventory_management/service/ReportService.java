package com.inventory_management.service;

import com.inventory_management.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    ReportSummaryDTO getSummary();
    List<SalesReportDTO> getSalesReport(LocalDate startDate, LocalDate endDate);
    List<InventoryReportDTO> getInventoryReport();
    List<CustomerReportDTO> getCustomerReport();
    List<ProfitReportDTO> getProfitReport();
    List<SalesTrendDTO> getSalesTrend();
    List<TopSellingProductDTO> getTopSellingProducts();

}