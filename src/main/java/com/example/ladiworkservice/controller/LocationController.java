package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.model.Unit;
import com.example.ladiworkservice.repository.LocationRepository;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("location")
@CrossOrigin
public class LocationController extends BaseController<Location>{
    @Autowired
    LocationService locationService;
    @Autowired
    LocationRepository locationRepository;
    @Override
    protected BaseService<Location> getService() {
        return locationService;
    }
    @Override
    public BaseResponse create(@RequestBody Location location) throws JsonProcessingException {
        if(locationRepository.findAllLocationByUnitAndIp(location.getUnit().getId(),location.getIp())!=null ){
            return new BaseResponse(500, "Thiết bị chấm công đã tồn tại",null );
        }

        return new BaseResponse(200, "Tạo thành công!", this.getService().create(location));
    }

}
