package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.AccountWarehouse;

import java.util.List;

public interface AccountWarehouseRepository extends BaseRepository<AccountWarehouse> {
    public List<AccountWarehouse> findAllByWarehouseId(Long warehouseId);
    public List<AccountWarehouse> findAllByWarehouseIdAndStatus(Long warehouseId, int status);
}
