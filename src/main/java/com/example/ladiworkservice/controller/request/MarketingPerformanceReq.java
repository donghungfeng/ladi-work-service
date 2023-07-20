package com.example.ladiworkservice.controller.request;

import lombok.Data;

@Data
public class MarketingPerformanceReq {
    private Long startDate;
    private Long endDate;
    private String shopCode;
}
