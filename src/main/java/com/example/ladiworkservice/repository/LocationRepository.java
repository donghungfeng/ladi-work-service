package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Location;
import org.springframework.data.jpa.repository.Query;

public interface LocationRepository extends BaseRepository<Location>{
    @Query(value = "select * from location l where l.unit_id = :unitId and l.ip= :ip ", nativeQuery = true)
    Location findAllLocationByUnitAndIp(Long unitId,String ip);
    @Query(value = "select DISTINCT * from location l where  l.ip= :ip ", nativeQuery = true)
    Location findAllLocationByIp(String ip);


}
