package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;

public interface StatisticsRevenueService{
    public BaseResponse statisticRevenue(Long startDate, Long endDate, Long shop);
    public BaseResponse search(Long startDate, Long endDate, Long shop);
}
