package com.example.ladiworkservice.service;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Unit;

public interface UnitService extends BaseService<Unit> {
    public BaseResponse getAllUnit();
}
