package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfitDashboardDto {
    private Long profit;
    private Long profitPercent;
    private Double profitAfterRefund;
    private Long profitAfterRefundPercent;
    private Long netProfit;
    private Long netProfitPercent;
}
