package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.reponse.CreateListOrderGHSVResponse;
import com.example.ladiworkservice.shippingorders.ghn.request.ShippingOrdersRequest;
import com.example.ladiworkservice.model.OrderShipping;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderShippingService extends BaseService<OrderShipping> {
    CreateListOrderGHSVResponse createOrderFromGHSV(Object orderGHSV);
    BaseResponse shippingOrders(ShippingOrdersRequest shippingOrdersRequest, String jwt) throws JsonProcessingException;
}
