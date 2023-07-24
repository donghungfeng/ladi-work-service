package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Unit;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.UnitRepository;
import com.example.ladiworkservice.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitServiceImpl extends BaseServiceImpl<Unit> implements UnitService {
    @Autowired
    UnitRepository unitRepository;
    @Override
    protected BaseRepository<Unit> getRepository() {
        return unitRepository;
    }
    @Override
    public BaseResponse getAllUnit() {
        return new BaseResponse(200, "Ok", unitRepository.findAllByOrderByIdDesc());
    }

}
