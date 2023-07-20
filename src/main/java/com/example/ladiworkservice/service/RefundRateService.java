package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.RefundRate;

public interface RefundRateService {
    RefundRate findById(Long id);
    BaseResponse inupRefundRate(RefundRate refundRate);
}
