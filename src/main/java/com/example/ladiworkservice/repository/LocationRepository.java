package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.model.Log_work;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends BaseRepository<Location>{
    @Query(value = "select * from location l where l.unit_id = :unitId and l.name= :name ", nativeQuery = true)
    Location findAllLocationByUnitAndName(Long unitId,String name);
    @Query(value = "select * from location l where  l.name= :name ", nativeQuery = true)
    Location findAllLocationByName(String name);
    @Query(value = "SELECT l.* FROM location l JOIN log_work lw ON l.id = lw.location_id WHERE lw.type = 1 AND lw.unit_id = :unitId \n" +
            "ORDER BY lw.time DESC LIMIT 1 ;", nativeQuery = true)
    Location findAllLocationReSentByUnit(Long unitId);

}
