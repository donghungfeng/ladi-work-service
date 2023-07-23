package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Unit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends BaseRepository<Unit>{
    List<Unit> findAll();
}
