package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateWarehouseRequest;

public interface WarehouseService{
    public BaseResponse create(CreateWarehouseRequest createWarehouseRequest);
    public BaseResponse deleteById(Long id);
    public BaseResponse search(String filter, String sort, int size, int page);
    public BaseResponse statisticWarehouse(Long warehouseId);
}
