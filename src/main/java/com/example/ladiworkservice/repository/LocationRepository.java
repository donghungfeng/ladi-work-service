package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.model.Log_work;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationRepository extends BaseRepository<Location>{
    @Query(value = "select * from location l where l.unit_id = :unitId and l.name= :name and l.type= :type ", nativeQuery = true)
    Location findAllLocationByUnitAndTypeAndName(Long unitId,String name,int type);
}
