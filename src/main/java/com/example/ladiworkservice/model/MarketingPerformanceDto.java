package com.example.ladiworkservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketingPerformanceDto {
    private List<MarketingPerformanceDetail> details;
    private Long sumData;
    private Long sumOrder;
    private Double sumRevenue;
    private Double tlc;
    private Double adsSumData;
    private Double adsSumOrder;
    private Double adsSumRevenue;
    private Double sumAds;
}
