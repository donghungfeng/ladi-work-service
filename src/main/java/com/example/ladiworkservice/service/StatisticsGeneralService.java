package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Data;
import com.example.ladiworkservice.model.StatisticsGeneral;

import java.util.List;
import java.util.Map;

public interface StatisticsGeneralService extends BaseService<StatisticsGeneral>{
    public List<StatisticsGeneral> checkStatisticsGeneral(Data data);
    public BaseResponse statistic(Long startDate, Long endDate, Long shop);
    public BaseResponse statisticCost(Long startDate, Long endDate, Long shop);
//    public StatisticsGeneral statisticsCostComon(Long date, List<Cost> costList, Shop shop);
    public Map<Long,StatisticsGeneral> statisticsCostComon(Long startDate, Long endDate, Long shop);
}
