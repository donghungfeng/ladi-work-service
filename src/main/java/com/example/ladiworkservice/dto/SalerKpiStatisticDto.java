package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalerKpiStatisticDto {

    private String salerName;
    private Long totalOrderAssigned;
    private Long totalOrderProcessed;
    private Float orderProcessingRate;
    private Long totalOrderSuccess;
    private Float orderSuccessRate;
    private Double sales;
    private Double revenue;
    private Double actualRevenue;
    private Double averageOrderValue;
    private Float closeRate;

}
