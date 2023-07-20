package com.example.ladiworkservice.service.impl;


import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.SaleKpiStatisticResponse;
import com.example.ladiworkservice.controller.request.SaleKpiStatisticRequest;
import com.example.ladiworkservice.dto.MvpSaleKpiStatisticDTO;
import com.example.ladiworkservice.dto.SalerKpiStatisticDto;
import com.example.ladiworkservice.dto.SumSaleKpiStatisticDTO;
import com.example.ladiworkservice.model.Data;
import com.example.ladiworkservice.model.SalerKpiStatistics;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.DataRepository;
import com.example.ladiworkservice.repository.SaleKpiStatisticRepository;
import com.example.ladiworkservice.repository.ShopRepository;
import com.example.ladiworkservice.service.SaleKpiStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ladiworkservice.configurations.global_variable.OrderStatus;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SaleKpiStatisticImpl extends BaseServiceImpl<SalerKpiStatistics> implements SaleKpiStatisticService {

    @Autowired
    DataRepository dataRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    SaleKpiStatisticRepository saleKpiStatisticRepository;
    @Override
    protected BaseRepository<SalerKpiStatistics> getRepository() {
        return null;
    }
    @Override
    public void updateSalerKpiStatistics(Long date, String shopCode, Long saleID) {
        List<Data> listData1 = dataRepository.getDataByDateAndShopByNhanvienID(shopCode, saleID);
        List<Data> listData2 = dataRepository.getDataByDateAndShopByNhanvienIDAndsaleID( shopCode, saleID);
        List<Data> listData3 = dataRepository.getDataByDateAndShopByOrderSuccess(date, shopCode, saleID);
        Long totalOrderAssigned = this.getTotalOrderAssigned(listData1);
        Long totalOrderProcessed = this.getTotalOrderProcessed(listData1);
        Long totalOrderSuccess = this.getTotalOrderSuccess(listData2);
        double sales = this.getSales(listData2);
        double actualRevenue = this.getActualRevenue(listData3);
        double revenue = this.getRevenue(listData2);
        Long shopId= shopRepository.findShopByCode(shopCode).getId();
        saleKpiStatisticRepository.UpdateSalerKpiStatistics(date, saleID, totalOrderAssigned, totalOrderProcessed, totalOrderSuccess, sales, actualRevenue, revenue,shopId);
    }

    @Override
    public BaseResponse getSalerKpiStatistics(SaleKpiStatisticRequest request)  {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date startDateFormat = null;
        Date endDateFormat = null;
        try {
            startDateFormat = sf.parse(request.getStartDate());
            endDateFormat = sf.parse(request.getEndDate());
        } catch (ParseException e) {
            return new BaseResponse(400, "Invalid date format",null);
        }
        SimpleDateFormat tf = new SimpleDateFormat("yyyyMMdd");
        String startDate = tf.format(startDateFormat);
        String endDate = tf.format(endDateFormat);

        Long shopId= shopRepository.findShopByCode(request.getShopCode()).getId();
        List<SalerKpiStatisticDto> statistics = saleKpiStatisticRepository.getSalerKpiStatistic(Long.parseLong(startDate), Long.parseLong(endDate),shopId,request.getShopCode());

        
        // Xử lý tổng
        Long sumTotalOrderAssigned = getSumTotalOrderAssigned(statistics);
        Long sumTotalOrderProcessed = getSumTotalOrderProcessed(statistics);
        float sumOrderProcessingRate = 0;
        if (sumTotalOrderAssigned != 0) {
            sumOrderProcessingRate = Float.valueOf(df.format((sumTotalOrderProcessed / (sumTotalOrderAssigned * 1.0)) * 100));
        }
        Long sumTotalOrderSuccess = getSumTotalOrderSuccess(statistics);
        float sumOrderSuccessRate = 0;
        if (sumTotalOrderProcessed != 0) {
            sumOrderSuccessRate = Float.valueOf(df.format((sumTotalOrderSuccess / (sumTotalOrderProcessed * 1.0)) * 100));
        }

        double sumSales = getSumSales(statistics);
        double sumActualRevenue = getSumActualRevenue(statistics);
        double sumRevenue = getSumRevenue(statistics);

        double sumAverageOrderValue = 0;
        if (sumTotalOrderSuccess != 0) {
            sumAverageOrderValue = sumSales / sumTotalOrderSuccess;
        }

        float sumCloseRate = 0;
        if (sumTotalOrderAssigned != 0) {
            sumCloseRate = Float.valueOf(df.format((sumTotalOrderSuccess / (sumTotalOrderAssigned * 1.0)) * 100));
        }
        SumSaleKpiStatisticDTO sumSaleKpiStatisticDTO = new SumSaleKpiStatisticDTO();
        sumSaleKpiStatisticDTO.setSumTotalOrderAssigned(sumTotalOrderAssigned);
        sumSaleKpiStatisticDTO.setSumTotalOrderProcessed(sumTotalOrderProcessed);
        sumSaleKpiStatisticDTO.setSumOrderProcessingRate(Float.parseFloat(df.format(sumOrderProcessingRate)));
        sumSaleKpiStatisticDTO.setSumTotalOrderSuccess(sumTotalOrderSuccess);
        sumSaleKpiStatisticDTO.setSumOrderSuccessRate(Float.parseFloat(df.format(sumOrderSuccessRate)));
        sumSaleKpiStatisticDTO.setSumSales((double) Math.round(sumSales));
        sumSaleKpiStatisticDTO.setSumRevenue((double) Math.round(sumRevenue));
        sumSaleKpiStatisticDTO.setSumActualRevenue((double) Math.round(sumActualRevenue));
        sumSaleKpiStatisticDTO.setSumAverageOrderValue(Double.parseDouble(df.format(sumAverageOrderValue)));
        sumSaleKpiStatisticDTO.setSumCloseRate(Float.parseFloat(df.format(sumCloseRate)));

        // lấy danh sách mvp tháng hiện tại

        List<MvpSaleKpiStatisticDTO> mvpSaleKpiStatisticDTOList = saleKpiStatisticRepository.getMvpSalerKpiStatistic(request.getShopCode());


        return  new BaseResponse(200, "Ok",new SaleKpiStatisticResponse(statistics,sumSaleKpiStatisticDTO,mvpSaleKpiStatisticDTOList));
    }

    private Long getTotalOrderAssigned(List<Data> listData) {
        return listData.stream()
                .filter(e -> OrderStatus.STATUS_ORDER_ASSIGNED.contains(e.getStatus()))
                .count();
    }
    private Long getTotalOrderProcessed(List<Data> listData){
        return listData.stream().
                filter(e -> OrderStatus.STATUS_ORDER_PROCESSED.contains(e.getStatus()))
                .count();
    }
    private Long getTotalOrderSuccess(List<Data> listData){
        return listData.stream()
                .filter(e -> OrderStatus.STATUS_ORDER_SUCCESS.contains(e.getStatus()))
                .count();
    }
    private Double getSales(List<Data> listData){
        return listData.stream().filter(e -> OrderStatus.STATUS_ORDER_SUCCESS.contains(e.getStatus()))
                .filter(e -> e.getPrice() !=null )
                .mapToDouble(e -> e.getPrice())
                .sum();
    }
    private Double getRevenue(List<Data> listData){
        return listData.stream().filter(e -> OrderStatus.STATUS_ORDER_ESTIMATED_REVENUE.contains(e.getStatus()))
                .filter(e -> e.getPrice() !=null )
                .mapToDouble(e -> e.getPrice())
                .sum();
    }
    private Double getActualRevenue(List<Data> listData){
        return listData.stream().filter(e -> OrderStatus.STATUS_ORDER_ACTUAL_REVENUE.contains(e.getStatus()))
                .filter(e -> e.getPrice() !=null )
                .mapToDouble(e -> e.getPrice())
                .sum();
    }
    private Long getSumTotalOrderAssigned(List<SalerKpiStatisticDto> listData){
        return listData.stream()
                .filter(e -> e.getTotalOrderAssigned()!=null)
                .mapToLong(e->e.getTotalOrderAssigned())
                .sum();
    }
    private Long getSumTotalOrderProcessed(List<SalerKpiStatisticDto> listData){
        return listData.stream().
                filter(e -> e.getTotalOrderProcessed() !=null)
                .mapToLong(e->e.getTotalOrderProcessed())
                .sum();
    }
    private Long getSumTotalOrderSuccess(List<SalerKpiStatisticDto> listData){
        return listData.stream()
                .filter(e -> e.getTotalOrderSuccess() !=null)
                .mapToLong(e->e.getTotalOrderSuccess())
                .sum();
    }
    private Double getSumSales(List<SalerKpiStatisticDto> listData){
        return listData.stream().filter(e -> e.getSales() !=null)
                .mapToDouble(e -> e.getSales())
                .sum();
    }
    private Double getSumRevenue(List<SalerKpiStatisticDto> listData){
        return listData.stream().filter(e -> e.getRevenue()!=null)
                .mapToDouble(e -> e.getRevenue())
                .sum();
    }
    private Double getSumActualRevenue(List<SalerKpiStatisticDto> listData){
        return listData.stream().filter(e -> e.getActualRevenue()!=null)
                .mapToDouble(e -> e.getActualRevenue())
                .sum();
    }



}
