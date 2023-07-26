package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.model.Log_work;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends BaseRepository<Location>{
    @Query(value = "select * from location l where l.unit_id = :unitId and l.ip= :ip ", nativeQuery = true)
    Location findAllLocationByUnitAndIp(Long unitId,String ip);
    @Query(value = "select DISTINCT * from location l where  l.ip= :ip ", nativeQuery = true)
    Location findAllLocationByIp(String ip);
    @Query(value = "SELECT l.* FROM location l JOIN log_work lw ON l.id = lw.location_id WHERE lw.type = 1 AND lw.unit_id = :unitId \n" +
            "ORDER BY lw.time DESC LIMIT 1 ;", nativeQuery = true)
    Location findAllLocationReSentByUnit(Long unitId);

}
