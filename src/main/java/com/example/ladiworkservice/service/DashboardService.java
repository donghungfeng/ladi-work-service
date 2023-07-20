package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;

public interface DashboardService {
    BaseResponse getDataDashboard(String shopCode, Long fromDate, Long toDate);
    BaseResponse getDataRevenueStatistics(String shopCode, Long fromDate, Long toDate);
    BaseResponse getCostData(String shopCode, Long fromDate, Long toDate);
    BaseResponse getSaleUtmDto(String shopCode, Long fromDate, Long toDate);
    BaseResponse getMarketingUtmDto(String shopCode, Long fromDate, Long toDate);
    BaseResponse getCostMarketingGroupByName(String shopCode, Long fromDate, Long toDate);
    BaseResponse getProductWareHouseChartDto(Long fromDate, Long toDate);
}
