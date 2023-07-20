package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Data;
import org.springframework.data.jpa.repository.Query;

public interface StatisticDataReponsitory extends BaseRepository<Data>{
    @Query(value = "SELECT SUM(price) from data where status in (7,8,10,12,13,14,15,16,17,18,19) and date >= :startDate and date <= :endDate and shopcode = :shopCode", nativeQuery = true)
    Long statisticSales(Long startDate, Long endDate, String shopCode);

    @Query(value = "SELECT SUM(price) from data where status in (7,8,10,12,13,14,15) and date >= :startDate and date <= :endDate and shopcode = :shopCode", nativeQuery = true)
    Long statisticNetSales(Long startDate, Long endDate, String shopCode);

    @Query(value = "SELECT SUM(cost) from data where status in (7,8,10,12,13,14,15,16,17,18,19) and date >= :startDate and date <= :endDate and shopcode = :shopCode", nativeQuery = true)
    Long statisticCostDeliveryFee(Long startDate, Long endDate, String shopCode);

    @Query(value = "SELECT SUM(cost) from data where status in (16,17,18,19) and date >= :startDate and date <= :endDate and shopcode = :shopCode", nativeQuery = true)
    Long statisticCostReturnGood(Long startDate, Long endDate, String shopCode);


}
