package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.Log_workRequest;
import com.example.ladiworkservice.model.Log_work;
import org.springframework.stereotype.Service;


public interface Log_workService extends BaseService<Log_work> {
    public BaseResponse checkIn(Log_workRequest logWorkRequest);
}
