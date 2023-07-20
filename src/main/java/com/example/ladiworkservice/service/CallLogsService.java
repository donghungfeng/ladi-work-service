package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.CallLogs;


public interface CallLogsService extends BaseService<CallLogs>{
    public BaseResponse statisticCallLogsByCallDate();
    public BaseResponse statisticCallLogs(String fromCallDate, String toCallDate);
    public BaseResponse getAllCallLogsByDuration(String sortDirection);
}
