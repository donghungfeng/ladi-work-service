package com.example.ladiworkservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MarketingPerformanceDetail {
    private String account;
    private String fullName;
    private Integer totalData = 0;
    private Integer totalOrder = 0;
    private Double revenue = 0D;
    private Double adsCost = 0D;
    private Double adsTotalData = 0D;
    private Double adsTotalOrder = 0D;
    private Double adsRevenue = 0D;
    private Double tlc = 0D; // totalOrder/totalData

    public static MarketingPerformanceDetail get(String account, String fullName) {
        MarketingPerformanceDetail detail = new MarketingPerformanceDetail();
        detail.setAccount(account);
        detail.setFullName(fullName);
        return detail;
    }
}
