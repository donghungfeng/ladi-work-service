package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.StatisticGeneralResponse;
import com.example.ladiworkservice.model.Cost;
import com.example.ladiworkservice.model.Data;
import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.model.StatisticsGeneral;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.repository.*;
import com.example.ladiworkservice.service.StatisticsGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsGeneralServiceImpl extends BaseServiceImpl<StatisticsGeneral> implements StatisticsGeneralService {
    @Autowired
    StatisticsGeneralRepository statisticsGeneralRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    DataRepository dataRepository;
    @Autowired
    CostTypeRepository costTypeRepository;
    @Autowired
    CostRepository costRepository;
    @Override
    protected BaseRepository<StatisticsGeneral> getRepository() {
        return statisticsGeneralRepository;
    }

    @Override
    public List<StatisticsGeneral> checkStatisticsGeneral(Data data) {
        Page<StatisticsGeneral> statisticsGeneralPage = (Page<StatisticsGeneral>) search("date=="+data.getDateOnly()+";shop.code=="+data.getShopCode(), "id,asc", 1, 0).RESULT;
        List<StatisticsGeneral> statisticsGeneralList = statisticsGeneralPage.getContent();
        return statisticsGeneralList;
    }

    @Override
    public BaseResponse statistic(Long startDate, Long endDate, Long shop) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<LocalDate> listOfDates = findNumOfDay(startDate, endDate, formatter);
        List<StatisticsGeneral> resultList = new ArrayList<>();
        Shop tempShop = shopRepository.findAllById(shop);
        List<StatisticsGeneral> statisticsGeneralList = statisticsGeneralRepository.findAllByRangeDate(startDate, endDate, shop);
        Map<Long, StatisticsGeneral> statisticsGeneralMap = new HashMap<>();
        for (StatisticsGeneral item : statisticsGeneralList){
            statisticsGeneralMap.put(item.getDate(), item);
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
            StatisticsGeneral statisticsGeneral = statisticsGeneralMap.get(date);
            if (statisticsGeneral == null){
                statisticsGeneral = new StatisticsGeneral();
                statisticsGeneral.setDate(date);
                statisticsGeneral.setShop(tempShop);
            }
            StatisticGeneralResponse statisticGeneralResponse = dataRepository.statisticsGeneral(date, date, tempShop.getCode());
            statisticsGeneral.setShippingCost(statisticGeneralResponse.getShippingCost() !=null ? statisticGeneralResponse.getShippingCost().longValue():0L);
            statisticsGeneral.setSales(statisticGeneralResponse.getSales() != null? statisticGeneralResponse.getSales().longValue() : 0L);
            statisticsGeneral.setCostPrice(statisticGeneralResponse.getCostPrice()!= null ? statisticGeneralResponse.getCostPrice() : 0L);
            statisticsGeneral.setTotalOrder(statisticGeneralResponse.getTotalOrder() != null ? statisticGeneralResponse.getTotalOrder() : 0L);
            resultList.add(statisticsGeneral);
        }
        return new BaseResponse().success(statisticsGeneralRepository.saveAll(resultList));
    }

    @Override
    public BaseResponse statisticCost(Long startDate, Long endDate, Long shop) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<LocalDate> listOfDates = findNumOfDay(startDate, endDate, formatter);
        Shop tempShop = shopRepository.findAllById(shop);
        List<StatisticsGeneral> resultList = new ArrayList<>();
        Map<Long, StatisticsGeneral> statisticsGeneralCostMap = statisticsCostComon(startDate, endDate, shop);
        List<StatisticsGeneral> statisticsGeneralList = statisticsGeneralRepository.findAllByRangeDate(startDate, endDate, shop);
        Map<Long, StatisticsGeneral> statisticsGeneralMap = statisticsCostComon(startDate, endDate, shop);
        for (StatisticsGeneral item : statisticsGeneralList){
            statisticsGeneralMap.put(item.getDate(), item);
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
            StatisticsGeneral statisticsGeneral = statisticsGeneralMap.get(date);
            StatisticsGeneral statisticCost = statisticsGeneralCostMap.get(date);
            if (statisticsGeneral == null){
                statisticsGeneral = new StatisticsGeneral();
                statisticsGeneral.setDate(date);
                statisticsGeneral.setShop(tempShop);
            }
            statisticsGeneral.setOtherCost(statisticCost.getOtherCost() != null ? statisticCost.getOtherCost() : 0.0);
            statisticsGeneral.setOperatingCost(statisticCost.getOperatingCost() != null ? statisticCost.getOperatingCost() : 0.0);
            statisticsGeneral.setMktCost(statisticCost.getMktCost() != null ? statisticCost.getMktCost() : 0.0);
            resultList.add(statisticsGeneral);
        }
        return new BaseResponse().success(statisticsGeneralRepository.saveAll(resultList));
    }

//    @Override
//    public StatisticsGeneral statisticsCostComon(Long date, List<Cost> costList, Shop tempShop){
//        StatisticsGeneral statisticsGeneral = new StatisticsGeneral();
//        statisticsGeneral.setDate(date);
//        statisticsGeneral.setShop(tempShop);
//        Double operatingCost = 0.0;
//        Double mktCost = 0.0;
//        Double otherCost = 0.0;
//        for (Cost item : costList){
//            if (item.getFromDate() <= date && item.getToDate() >= date){
//                switch (item.getCostType().getCode()){
//                    case "CPCD": case "CPHT":
//                        operatingCost += item.getCostPerDay();
//                        break;
//                    case "CPK":
//                        otherCost += item.getCostPerDay();
//                        break;
//                    case "CPMKT":
//                        mktCost += item.getCostPerDay();
//                        break;
//                }
//            }
//        }
//        statisticsGeneral.setMktCost(mktCost);
//        statisticsGeneral.setOtherCost(otherCost);
//        statisticsGeneral.setOperatingCost(operatingCost);
//        return statisticsGeneral;
//    }

    @Override
    public Map<Long,StatisticsGeneral> statisticsCostComon(Long startDate, Long endDate, Long shop){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<LocalDate> listOfDates = findNumOfDay(startDate, endDate, formatter);
        Shop tempShop = shopRepository.findAllById(shop);
        List<Cost> costList = costRepository.findCostByTimeRange(startDate, endDate, tempShop.getCode());
        Map<Long, StatisticsGeneral> statisticsGeneralMap = new HashMap<>();
        for (int i = 0; i<listOfDates.size()+1; i++){
            Double operatingCost = 0.0;
            Double mktCost = 0.0;
            Double otherCost = 0.0;
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
            StatisticsGeneral statisticsGeneral = new StatisticsGeneral();
            statisticsGeneral.setDate(date);
            statisticsGeneral.setShop(tempShop);
            for (Cost item : costList){
                if (item.getFromDate() <= date && item.getToDate() >= date){
                    switch (item.getCostType().getCode()){
                        case "CPCD": case "CPHT":
                            operatingCost += item.getCostPerDay();
                            break;
                        case "CPK":
                            otherCost += item.getCostPerDay();
                            break;
                        case "CPMKT":
                            mktCost += item.getCostPerDay();
                            break;
                    }
                }
            }
            statisticsGeneral.setMktCost(mktCost);
            statisticsGeneral.setOtherCost(otherCost);
            statisticsGeneral.setOperatingCost(operatingCost);
            statisticsGeneralMap.put(statisticsGeneral.getDate(), statisticsGeneral);
        }
        return statisticsGeneralMap;
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
