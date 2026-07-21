package com.inventory_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class StockMovementStatsDTO {


    private long totalMovements;

    private long totalIn;

    private long totalOut;

    private long todayMovements;


}