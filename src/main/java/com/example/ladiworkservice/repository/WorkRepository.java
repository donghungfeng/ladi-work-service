package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.dto.StatisticPerformanceSaleDto;
import com.example.ladiworkservice.model.Account;
import com.example.ladiworkservice.model.Work;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends BaseRepository<Work>{
    List<Work> findAllByIsActive(int isActive);
    Work findAllByIsActiveAndAccount(int isActive, Account account);
    List<Work> findAllByAccount(Account account);

    @Query(nativeQuery = true)
    List<StatisticPerformanceSaleDto> statisticPerformanceSale(Long startDate, Long endDate, String shopCode);
}
