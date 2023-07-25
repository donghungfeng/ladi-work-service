package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.LocationRepository;
import com.example.ladiworkservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends BaseServiceImpl<Location> implements LocationService {
    @Autowired
    LocationRepository locationRepository;
    @Override
    protected BaseRepository<Location> getRepository() {
        return locationRepository;
    }
    @Override
    public BaseResponse getAllLocationReSentByUnit(Long unitId) {
        return new BaseResponse(200, "Ok", locationRepository.findAllLocationReSentByUnit(unitId));
    }
}
