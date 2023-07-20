package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Config;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConfigRepository extends BaseRepository<Config> {
    public Config findAllByCode(String code);

    @Query(value = "select * from config where from_date >= :startDate and to_date <= :endDate", nativeQuery = true)
    List<Config> findConfigByDate(Long startDate, Long endDate);

    Optional<Config> getByCode(String code);
}
