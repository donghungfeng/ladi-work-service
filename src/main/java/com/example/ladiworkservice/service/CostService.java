package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.PostCostRequest;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.Cost;

import java.util.List;

public interface CostService extends BaseService<Cost>{
    public BaseResponse postCost(PostCostRequest postCostRequest);
    public BaseResponse getCost();
    public BaseResponse laySoDonTheoThoiGian(String startDate, String endDate);
    public BaseResponse getAllCostByTimeRange(String startDate, String endDate,String shopCode, String jwt);
    public BaseResponse createCostTransport();
    List<Cost> getByDateAndAccountAndShop(Long startDate, Long endDate, Account account, String shopCode);
}
