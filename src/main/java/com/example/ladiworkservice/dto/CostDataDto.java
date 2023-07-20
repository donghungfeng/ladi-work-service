package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostDataDto {
    private BigInteger date = BigInteger.valueOf(0);
    private BigDecimal totalCost = BigDecimal.valueOf(0.0);
    private BigDecimal averageCost = BigDecimal.valueOf(0.0);
}
