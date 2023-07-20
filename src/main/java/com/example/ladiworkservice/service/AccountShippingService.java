package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.AccountShipping;

public interface AccountShippingService  extends BaseService<AccountShipping>{
    BaseResponse findShopByToken(Long id);
    BaseResponse checkConnect(Long shippingType, String token);
}
