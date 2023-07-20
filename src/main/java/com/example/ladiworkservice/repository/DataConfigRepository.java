package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.DataConfig;
import com.example.ladiworkservice.model.Shop;

import java.util.List;

public interface DataConfigRepository extends BaseRepository<DataConfig> {
    List<DataConfig> getByShop(Shop shop);

    List<DataConfig> getByShop_Id(Long shopId);
}
