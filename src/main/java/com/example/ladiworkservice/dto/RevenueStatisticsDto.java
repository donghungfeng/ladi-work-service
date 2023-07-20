package com.example.ladiworkservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueStatisticsDto {
    private BigInteger date = BigInteger.valueOf(0);
    private Double averagePrice = 0.0;
    private Double revenue = 0.0;
    private BigDecimal totalOrder = BigDecimal.valueOf(0.0);
    private BigDecimal averageOrder = BigDecimal.valueOf(0.0);
    private Double sales =  0.0;
    @JsonIgnore
    private Double saleCostPrice = 0.0;
    @JsonIgnore
    private Double averageSaleCostPrice = 0.0;
    private Double averageRevenue = 0.0;
}
