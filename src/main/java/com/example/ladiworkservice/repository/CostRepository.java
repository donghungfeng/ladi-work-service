package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.dto.SoDonTheoThoiGianDto;
import com.example.ladiworkservice.model.Cost;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CostRepository extends BaseRepository<Cost>{
    @Query(nativeQuery = true)
    SoDonTheoThoiGianDto laySoDonTheoThoiGian(Long startDate, Long endDate);

    @Query(value = "select * from cost where from_date >= :startDate and to_date <= :endDate and shopcode = :shopCode", nativeQuery = true)
    List<Cost> findAllCostByTimeRange(Long startDate, Long endDate, String shopCode);

    @Query(value = "select * from cost where from_date >= :startDate and to_date <= :endDate and name = :userName and shopcode = :shopCode", nativeQuery = true)
    List<Cost> findAllCostByTimeRangeAndName(Long startDate, Long endDate, String userName, String shopCode);

    @Query(value = "select * from cost where (to_date >= :startDate or from_date <= :endDate)  and shopcode = :shopCode", nativeQuery = true)
    List<Cost> findCostByTimeRange(Long startDate, Long endDate, String shopCode);
}
