package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.dto.*;
import com.example.ladiworkservice.dto.*;

import java.util.List;

public interface DashboardRepository {
    RevenueInfoDashboard getDataRevenueInfoDashboard(String shopCode, Long fromDate, Long toDate);
    CostDataDashBoardDto getCostDataDashboard(String shopCode, Long fromDate, Long toDate);
    ProductSoldDashboardDto getProductSoldDashboard(String shopCode, Long fromDate, Long toDate);
    List<RevenueStatisticsDto> getRevenueStatisticsData(String shopCode, Long fromDate, Long toDate);
    List<CostDataDto> getCostData(String shopCode, Long fromDate, Long toDate);
    List<SaleUtmDto> getSaleUtmDto(String shopCode, Long fromDate, Long toDate);
    List<MarketingUtmDto> getMarketingUtmDto(String shopCode, Long fromDate, Long toDate);
    List<CostMarketingGroupByName> getCostMarketingGroupByName(String shopCode, Long fromDate, Long toDate);
    ProductWarehouseDto getProductWarehouseDto(String shopCode, Long fromDate, Long toDate);
    List<ProductWareHouseChartDto> getProductWareHouseChartDto(Long fromDate, Long toDate);
}
