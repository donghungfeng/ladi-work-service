package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.model.CostType;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.CostTypeRepository;
import com.example.ladiworkservice.service.CostTypeService;
import org.springframework.stereotype.Service;

@Service
public class CostTypeServiceImpl extends BaseServiceImpl<CostType> implements CostTypeService {
    private CostTypeRepository costTypeRepository;

    public CostTypeServiceImpl(CostTypeRepository costTypeRepository){
        this.costTypeRepository = costTypeRepository;
    }
    @Override
    protected BaseRepository<CostType> getRepository() {
        return costTypeRepository;
    }
}
