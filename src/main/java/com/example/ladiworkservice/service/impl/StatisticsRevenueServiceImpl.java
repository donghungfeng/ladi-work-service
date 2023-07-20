package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.StatisticsRevenueResponse;
import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.model.StatisticsGeneral;
import com.example.ladiworkservice.model.StatisticsRevenue;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.repository.StatisticsRevenueReponsitory;
import com.example.ladiworkservice.service.StatisticsGeneralService;
import com.example.ladiworkservice.service.StatisticsRevenueService;
import com.example.ladiworkservice.repository.CostRepository;
import com.example.ladiworkservice.repository.DataRepository;
import com.example.ladiworkservice.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsRevenueServiceImpl implements StatisticsRevenueService {
    @Autowired
    StatisticsRevenueReponsitory statisticsRevenueReponsitory;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    StatisticsGeneralService statisticsGeneralService;

    @Autowired
    DataRepository dataRepository;

    @Autowired
    CostRepository costRepository;

    @Override
    public BaseResponse statisticRevenue(Long startDate, Long endDate, Long shop) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<LocalDate> listOfDates = findNumOfDay(startDate, endDate, formatter);
        Shop tempShop = shopRepository.findAllById(shop);
        Map<Long, StatisticsGeneral> statisticsGeneralMap = statisticsGeneralService.statisticsCostComon(startDate, endDate, shop);
        List<StatisticsRevenue> resultList = new ArrayList<>();
        List<StatisticsRevenue> statisticsRevenueList = statisticsRevenueReponsitory.findAllByRangeDate(startDate, endDate, shop);
        Map<Long, StatisticsRevenue> statisticsRevenueMap = new HashMap<>();
        for (StatisticsRevenue item : statisticsRevenueList){
            statisticsRevenueMap.put(item.getDate(), item);
        }
        for (int i = 0; i<listOfDates.size()+1; i++){
            Long date;
            if (listOfDates.size() == 0){
                date = startDate;
            }else {
                if (i == listOfDates.size()){
                    date = Long.parseLong(listOfDates.get(i-1).plusDays(1).format(formatter));
                }else {
                    date = Long.parseLong(listOfDates.get(i).format(formatter));
                }
            }
//            List<Cost> costList = costRepository.findAllCostByTimeRange(date, tempShop.getCode());
            StatisticsRevenue statisticsRevenue = statisticsRevenueMap.get(date);
            if (statisticsRevenue == null){
                statisticsRevenue = new StatisticsRevenue();
                statisticsRevenue.setDate(date);
                statisticsRevenue.setShop(tempShop);
            }
//            StatisticsGeneral statisticCost = statisticsGeneralService.statisticsCostComon(date, costList, tempShop);
            StatisticsGeneral statisticCost = statisticsGeneralMap.get(date);
            StatisticsRevenueResponse statisticsRevenueResponse = dataRepository.statisticsRevenue(Long.parseLong(String.valueOf(startDate) + "000000"), Long.parseLong(String.valueOf(endDate) + "235959"), tempShop.getCode());
            statisticsRevenue.setCostPrice(statisticsRevenueResponse.getCostPrice() != null?statisticsRevenueResponse.getCostPrice() : 0L);
            statisticsRevenue.setShippingCost(statisticsRevenueResponse.getShippingCost() != null ? statisticsRevenueResponse.getShippingCost().longValue() : 0L);
            statisticsRevenue.setRevenue(statisticsRevenueResponse.getRevenue() != null ? statisticsRevenueResponse.getRevenue().longValue() : 0L);
            statisticsRevenue.setOperatingCost(statisticCost.getOperatingCost());
            statisticsRevenue.setOtherCost(statisticCost.getOtherCost());
            statisticsRevenue.setMktCost(statisticCost.getMktCost());
            statisticsRevenue.setTotalOrder(statisticsRevenueResponse.getTotalOrder() != null ? statisticsRevenueResponse.getTotalOrder() : 0L);
            statisticsRevenue.setProfit(statisticsRevenue.getRevenue() - (statisticsRevenue.getCostPrice() +
                    statisticsRevenue.getShippingCost() + statisticsRevenue.getMktCost() +
                    statisticsRevenue.getOperatingCost() + statisticsRevenue.getOtherCost()));
            resultList.add(statisticsRevenue);

        }
        return new BaseResponse().success(statisticsRevenueReponsitory.saveAll(resultList));
    }

    @Override
    public BaseResponse search(Long startDate, Long endDate, Long shop){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<LocalDate> listOfDates = findNumOfDay(startDate, endDate, formatter);
        Shop tempShop = shopRepository.findAllById(shop);
        Map<Long, StatisticsGeneral> statisticsGeneralMap = statisticsGeneralService.statisticsCostComon(startDate, endDate, shop);
        List<StatisticsRevenue> resultList = new ArrayList<>();
        List<StatisticsRevenue> statisticsRevenueList = statisticsRevenueReponsitory.findAllByRangeDate(startDate, endDate, shop);
        Map<Long, StatisticsRevenue> statisticsRevenueMap = new HashMap<>();
        for (StatisticsRevenue item : statisticsRevenueList){
            statisticsRevenueMap.put(item.getDate(), item);
        }
        for (int i = 0; i<listOfDates.size()+1; i++){
            Long date;
            if (listOfDates.size() == 0){
                date = startDate;
            }else {
                if (i == listOfDates.size()){
                    date = Long.parseLong(listOfDates.get(i-1).plusDays(1).format(formatter));
                }else {
                    date = Long.parseLong(listOfDates.get(i).format(formatter));
                }
            }
//            List<Cost> costList = costRepository.findAllCostByTimeRange(date, tempShop.getCode());
            StatisticsRevenue statisticsRevenue = statisticsRevenueMap.get(date);
            if (statisticsRevenue == null){
                statisticsRevenue = new StatisticsRevenue();
                statisticsRevenue.setDate(date);
                statisticsRevenue.setShop(tempShop);
            }
//            StatisticsGeneral statisticCost = statisticsGeneralService.statisticsCostComon(date, costList, tempShop);
            StatisticsGeneral statisticCost = statisticsGeneralMap.get(date);
            statisticsRevenue.setOperatingCost(statisticCost.getOperatingCost());
            statisticsRevenue.setOtherCost(statisticCost.getOtherCost());
            statisticsRevenue.setMktCost(statisticCost.getMktCost());
            Double costPrice = statisticsRevenue.getCostPrice() != null ? statisticsRevenue.getCostPrice():0.0;
            Double shippingCost = statisticsRevenue.getShippingCost() != null ? statisticsRevenue.getShippingCost() : 0.0;
            Double mktCost = statisticsRevenue.getMktCost() != null ? statisticsRevenue.getMktCost() : 0.0;
            Double operatingCost = statisticsRevenue.getOperatingCost() != null ? statisticsRevenue.getOperatingCost() : 0.0;
            Double otherCost = statisticsRevenue.getOtherCost() != null ? statisticsRevenue.getOtherCost() : 0.0;
            Double revenue = statisticsRevenue.getRevenue() != null?statisticsRevenue.getRevenue() : 0.0;
            statisticsRevenue.setProfit(revenue - (costPrice + shippingCost + mktCost + otherCost + operatingCost));
            resultList.add(statisticsRevenue);

        }
        return new BaseResponse().success(statisticsRevenueReponsitory.saveAll(resultList));
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "GMT+7")
    public void statisticRevenueCronjob(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate now = LocalDate.now();
        List<Shop> shopList = shopRepository.findAll();
        for (Shop shop : shopList){
            statisticRevenue(Long.parseLong(now.minusDays(1).format(formatter)), Long.parseLong(now.minusDays(1).format(formatter)), shop.getId());
        }
    }
    private List<LocalDate> findNumOfDay(Long startDate, Long endDate, DateTimeFormatter formatter){
        String sStartDate = String.valueOf(startDate);
        String sEndDate = String.valueOf(endDate);
        LocalDate lStartDate = LocalDate.parse(sStartDate, formatter);
        LocalDate lEndDate = LocalDate.parse(sEndDate, formatter);
        List<LocalDate> listOfDates = lStartDate.datesUntil(lEndDate)
                .collect(Collectors.toList());

        return listOfDates;
    }

}
