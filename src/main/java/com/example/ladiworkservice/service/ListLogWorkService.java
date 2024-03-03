package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.ListLogWork;

public interface ListLogWorkService extends BaseService<ListLogWork>{
    public BaseResponse findDataByDate(Long startDate, Long endDate);
}
