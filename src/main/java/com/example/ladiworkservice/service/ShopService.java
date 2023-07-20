package com.example.ladiworkservice.service;

import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.controller.reponse.BaseResponse;

public interface ShopService extends BaseService<Shop> {
    BaseResponse getAllByStatus(int status, String jwt);

    Shop findById(Long id);

    Shop getByName(String name);
}
