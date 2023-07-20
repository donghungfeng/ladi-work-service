package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketingUtmDto {
    private String utmMedium;
    private BigInteger count;
    private BigDecimal totalOrder;
    private BigDecimal orderAcceptRate;
    private BigDecimal costMarketing;
}
