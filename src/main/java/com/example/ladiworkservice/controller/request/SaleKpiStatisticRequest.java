package com.example.ladiworkservice.controller.request;

import lombok.Data;

@Data
public class SaleKpiStatisticRequest {
    private String startDate;
    private String endDate;
    private String shopCode;
}
