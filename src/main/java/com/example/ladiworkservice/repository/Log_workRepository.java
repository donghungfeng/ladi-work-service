package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Log_work;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface Log_workRepository extends BaseRepository<Log_work>{
    @Query(value = "SELECT *\n" +
            "FROM log_work lw\n" +
            "WHERE JSON_EXTRACT(lw.data_received, '$.code') = :code AND lw.unit_id = :unit_id " +
            "AND lw.time BETWEEN :startDate AND :endDate ORDER BY id DESC;", nativeQuery = true)
    List<Log_work> findLogWorkByUser(String code, Long unit_id,Long startDate, Long endDate);

}
