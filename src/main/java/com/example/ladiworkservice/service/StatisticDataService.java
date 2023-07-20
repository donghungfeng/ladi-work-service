package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;

public interface StatisticDataService {
    BaseResponse revenue(Long startDate, Long endDate, String shopCode);
}
