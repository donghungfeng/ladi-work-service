package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.StatisticsRevenue;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsRevenueReponsitory extends BaseRepository<StatisticsRevenue>{
    @Query(value = "SELECT * FROM statistics_revenue where date >= :startDate and date <= :endDate and shop = :shop", nativeQuery = true)
    public List<StatisticsRevenue> findAllByRangeDate(Long startDate, Long endDate, Long shop);
}
