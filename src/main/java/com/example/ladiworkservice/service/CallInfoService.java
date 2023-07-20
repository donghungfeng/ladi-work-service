package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.CallInfo;

public interface CallInfoService extends BaseService<CallInfo>{
    public BaseResponse getCallActive();
    public BaseResponse active(Long id);
    public BaseResponse deActive(Long id);
    public BaseResponse heathCheck();
}
