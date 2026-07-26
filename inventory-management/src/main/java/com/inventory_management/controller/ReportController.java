package com.inventory_management.controller;

import com.inventory_management.dto.response.*;
import com.inventory_management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportSummaryDTO> getSummary() {

        return ResponseEntity.ok(
                reportService.getSummary()
        );

    }

    @GetMapping("/sales")
public List<SalesReportDTO> getSalesReport(

        @RequestParam(required = false)
        LocalDate startDate,


        @RequestParam(required = false)
        LocalDate endDate

){

    return reportService.getSalesReport(
            startDate,
            endDate
    );

}

    @GetMapping("/inventory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InventoryReportDTO>> getInventoryReport(){

        return ResponseEntity.ok(
                reportService.getInventoryReport()
        );

    }

    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<CustomerReportDTO>> getCustomerReport(){

        return ResponseEntity.ok(
                reportService.getCustomerReport()
        );

    }

    @GetMapping("/profit")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfitReportDTO>> getProfitReport(){

        return ResponseEntity.ok(
                reportService.getProfitReport()
        );

    }

    @GetMapping("/sales-trend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SalesTrendDTO>> getSalesTrend(){

        return ResponseEntity.ok(
                reportService.getSalesTrend()
        );

    }

    @GetMapping("/top-selling")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TopSellingProductDTO>> getTopSellingProducts(){

        return ResponseEntity.ok(
                reportService.getTopSellingProducts()
        );

    }

}