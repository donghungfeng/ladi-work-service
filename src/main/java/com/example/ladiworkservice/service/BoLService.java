package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateBoLRequest;


public interface BoLService {
    public BaseResponse create(CreateBoLRequest createBoLRequest);
    public BaseResponse search(String filter, String sort, int size, int page);
    public BaseResponse statisticBoLByStatus(int type, String shopCode);
    public BaseResponse statisticBoL(int type, Long shopCode);
    public BaseResponse statisticHistoryBoL(String shopCode);
}
