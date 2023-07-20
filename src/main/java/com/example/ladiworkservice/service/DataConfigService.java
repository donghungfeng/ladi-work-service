package com.example.ladiworkservice.service;

import com.example.ladiworkservice.model.DataConfig;
import com.example.ladiworkservice.model.Shop;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface DataConfigService extends BaseService<DataConfig> {
    void initDefault() throws JsonProcessingException;

    void createForShop(Shop shop);

    DataConfig findById(Long id);
}
