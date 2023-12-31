package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CheckOutRequest;
import com.example.ladiworkservice.controller.request.CheckWorkActiveRequest;
import com.example.ladiworkservice.controller.request.CreateWorkRequest;
import com.example.ladiworkservice.model.Work;

public interface WorkService extends BaseService<Work> {
    public BaseResponse createWork(CreateWorkRequest createWorkRequest);
    public BaseResponse getAllWork(String jwt, String startDate, String endDate);
    public BaseResponse checkOut(CheckOutRequest checkOutRequest, String shopCode);
    public BaseResponse getAllActive(String shopCode);
    public BaseResponse checkWorkActive(CheckWorkActiveRequest checkWorkActive, String shopCode);
    public BaseResponse infoCheckout(Long id);
    public BaseResponse statisticPerformanceSale(String startDate, String endDate, String shopCode);
}
