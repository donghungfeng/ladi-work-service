package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.StatisticsGeneral;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsGeneralRepository extends BaseRepository<StatisticsGeneral> {
    @Query(value = "SELECT * FROM statistics_general where date >= :startDate and date <= :endDate and shop = :shop", nativeQuery = true)
    public List<StatisticsGeneral> findAllByRangeDate(Long startDate, Long endDate, Long shop);
}
