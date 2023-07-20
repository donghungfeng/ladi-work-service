package com.example.ladiworkservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDto {
    private RevenueInfoDashboard revenueDashboardData;
    private CostDashBoardDto costDashboardData;
    private ProductDashboardDto productDashboardData;
    private ProfitDashboardDto profitData;
    private ProductWarehouseDto productWareHouse;
}
