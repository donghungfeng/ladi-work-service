package com.example.ladiworkservice.dto;

import lombok.Data;

@Data
public class SumSaleKpiStatisticDTO {
    private Long sumTotalOrderAssigned;
    private Long sumTotalOrderProcessed;
    private Float sumOrderProcessingRate;
    private Long sumTotalOrderSuccess;
    private Float sumOrderSuccessRate;
    private Double sumSales;
    private Double sumRevenue;
    private Double sumActualRevenue;
    private Double sumAverageOrderValue;
    private Float sumCloseRate;
}
