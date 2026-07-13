package com.inventory_management.controller;


import com.inventory_management.dto.response.DashboardResponseDTO;

import com.inventory_management.service.DashboardService;


import lombok.RequiredArgsConstructor;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {


    private final DashboardService dashboardService;



    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboard(){


        return ResponseEntity.ok(

                dashboardService.getDashboard()

        );


    }


}