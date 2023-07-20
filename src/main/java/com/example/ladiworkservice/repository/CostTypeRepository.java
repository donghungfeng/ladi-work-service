package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.CostType;

public interface CostTypeRepository extends BaseRepository<CostType>{
    CostType findByCode(String code);

}
