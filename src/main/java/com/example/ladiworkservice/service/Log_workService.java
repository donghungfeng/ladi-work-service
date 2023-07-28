package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.Log_workRequest;
import com.example.ladiworkservice.model.Log_work;


public interface Log_workService extends BaseService<Log_work> {
    public BaseResponse checkIn(Log_workRequest logWorkRequest);
    public BaseResponse findLogWorkByUser(String code, Long unitId,Long startDate,Long endDate, int size, int page);
}
