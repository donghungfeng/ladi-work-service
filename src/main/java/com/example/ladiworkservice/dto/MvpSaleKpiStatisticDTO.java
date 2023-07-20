package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MvpSaleKpiStatisticDTO {
    private String salerName;
    private Double sales;
}
