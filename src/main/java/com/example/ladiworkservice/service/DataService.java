package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.AssignJobRequest;
import com.example.ladiworkservice.controller.request.MarketingPerformanceReq;
import com.example.ladiworkservice.dto.DataDto;
import com.example.ladiworkservice.model.Data;
import com.example.ladiworkservice.model.MarketingPerformanceDto;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.UpdateStatusDataWebhookGHSVRP;
import com.example.ladiworkservice.shippingorders.ghsv.request.UpdateStatusDataWebhookGHSVRQ;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service

public interface DataService extends BaseService<Data> {

    public BaseResponse getAllData(String jwt, String status, String startDate, String endDate, String shopCode,
                                   int page, int size);

    public BaseResponse createData(Data data, String shopCode);

    DataDto createV2(Object data, String shopCode) throws JsonProcessingException;

    public BaseResponse assignWork(String jwt, AssignJobRequest assignJobRequest) throws JsonProcessingException, NoSuchAlgorithmException;

    public BaseResponse getAllDataAccountNull(String status, String shopCode);

    public BaseResponse statisticByUtmMedium();

    public BaseResponse statisticcalrevenueByDay();

    public BaseResponse ketQuaThongKeUtm(String startDate, String endDate, String jwt);

    public BaseResponse ketQuaThongKeTopUtm(String startDate, String endDate, String shopCode);

    public BaseResponse statisticalRevenueByDate(String startDate, String endDate, String shopCode);

    public BaseResponse statisticUtmByDate(String startDate, String endDate, String shopCode);

    public BaseResponse statisticDataByDateAndStatus(String startDate, String endDate, String shopCode);

    public BaseResponse statisticDataByDateAndStatusAndUser(String startDate, String endDate, String shopCode,
            String userId);

    public BaseResponse statisticQuantityDataByDateAndStatus(String startDate, String endDate, String shopCode);

    public BaseResponse getAllByPhone(String phone, String shopCode);

    public BaseResponse resetCost(Double cost);

    public BaseResponse findUniqueShop(String startDate, String endDate, Long id);

    public BaseResponse statisticCostByStatusData(String startDate, String endDate, String shopCode);

    public BaseResponse searchByRole(String filter, String sort, int size, int page, String jwt);

    public BaseResponse updateStatusData(List<Data> datas);

    public BaseResponse updateStatusDataList(List<Long> ids, int status);

    public BaseResponse statisticRevenue(Long startDate, Long endDate, String shopCode);

    MarketingPerformanceDto getMarketingPerformanceReport(MarketingPerformanceReq req);

    public BaseResponse tracking(String code);

    public UpdateStatusDataWebhookGHSVRP updateStatusGHSV(UpdateStatusDataWebhookGHSVRQ body) throws JsonProcessingException, NoSuchAlgorithmException;

}
