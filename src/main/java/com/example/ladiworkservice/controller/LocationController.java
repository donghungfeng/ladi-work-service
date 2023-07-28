package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Location;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("location")
@CrossOrigin
public class LocationController extends BaseController<Location>{
    @Autowired
    LocationService locationService;
    @Override
    protected BaseService<Location> getService() {
        return locationService;
    }


}
