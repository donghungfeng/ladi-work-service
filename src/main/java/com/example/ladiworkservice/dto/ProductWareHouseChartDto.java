package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWareHouseChartDto {
    private BigInteger date = BigInteger.valueOf(0);
    private BigDecimal averageProduct = BigDecimal.valueOf(0.0);
    private BigDecimal productAvaiable = BigDecimal.valueOf(0.0);
}
