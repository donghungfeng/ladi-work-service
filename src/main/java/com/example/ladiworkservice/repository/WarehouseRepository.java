package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.controller.reponse.StatisticWarehouseReponse;
import com.example.ladiworkservice.model.Warehouse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseRepository extends BaseRepository<Warehouse>{
    public List<Warehouse> findAllByFlagAndId(int flag, Long id);
    @Query("SELECT new com.example.ladiworkservice.controller.reponse.StatisticWarehouseReponse(SUM(sp.totalQuantity), SUM(sp.inventoryQuantity), SUM(sp.awaitingProductQuantity), SUM(sp.inventoryQuantity * sp.lastImportedPrice)) FROM SubProduct sp where sp.warehouse.id = :warehouseId")
    StatisticWarehouseReponse statisticProduct(Long warehouseId);
}
