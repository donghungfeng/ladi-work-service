package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleUtmDto {
    private String utmMedium;
    private BigInteger count;
    private Double  averagePrice;
    private Double  revenue;
    private Double  sales;
    private BigDecimal  totalOrder;
    private BigDecimal orderAcceptRate;
}
