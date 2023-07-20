package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.StatisticDataResponse;
import com.example.ladiworkservice.repository.StatisticDataReponsitory;
import com.example.ladiworkservice.service.StatisticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticDataServiceImpl implements StatisticDataService {

    @Autowired
    StatisticDataReponsitory statisticDataReponsitory;

    @Override
    public BaseResponse revenue(Long startDate, Long endDate, String shopCode) {
        StatisticDataResponse statisticDataResponse = new StatisticDataResponse();

        // Doanh so
        Long sales = statisticDataReponsitory.statisticSales(startDate, endDate, shopCode);
        if (sales == null){
            statisticDataResponse.setSales(0L);
        }else {
            statisticDataResponse.setSales(sales);
        }

        // Doanh so sau hoan
        Long netSales = statisticDataReponsitory.statisticNetSales(startDate, endDate, shopCode);
        if (netSales == null){
            statisticDataResponse.setSales(0L);
        }else {
            statisticDataResponse.setNetSales(netSales);
        }

        // chi phi van chuyen truoc hoan
        Long costDeliveryFee = statisticDataReponsitory.statisticCostDeliveryFee(startDate, endDate, shopCode);
        if (costDeliveryFee == null){
            statisticDataResponse.setCostDeliveryFee(0L);
        }else {
            statisticDataResponse.setCostDeliveryFee(costDeliveryFee);
        }

        // chi phi van chuyen hang hoan
        Long costReturnGood = statisticDataReponsitory.statisticCostReturnGood(startDate, endDate, shopCode);
        if (costReturnGood == null){
            statisticDataResponse.setCostReturnGood(0L);
        }else {
            statisticDataResponse.setCostReturnGood(costReturnGood);
        }
        statisticDataResponse.setCostNetDeliveryFee(statisticDataResponse.getCostDeliveryFee() + statisticDataResponse.getCostReturnGood());
        statisticDataResponse.setRevenue(statisticDataResponse.getSales() - statisticDataResponse.getCostDeliveryFee());
        statisticDataResponse.setNetRevenue(statisticDataResponse.getNetSales() - statisticDataResponse.getCostNetDeliveryFee());
        return new BaseResponse().success(statisticDataResponse);
    }
}
