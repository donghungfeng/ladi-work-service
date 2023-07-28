package com.example.ladiworkservice.repository;


import com.example.ladiworkservice.model.Unit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UnitRepository extends BaseRepository<Unit> {
    List<Unit> findAll();
    @Query(value = "select * from unit u where u.id = :unitId ", nativeQuery = true)
    Unit findUnitById(Long unitId);
    Unit findByCode(String code);
}
