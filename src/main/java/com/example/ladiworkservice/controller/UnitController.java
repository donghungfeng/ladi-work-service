package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Unit;
import com.example.ladiworkservice.service.BaseService;

import com.example.ladiworkservice.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("unit")
@CrossOrigin
public class UnitController extends BaseController<Unit>{
   @Autowired
    UnitService unitService;
    @Override
    protected BaseService<Unit> getService() {
        return unitService;
    }
    @GetMapping("getAll")
    public BaseResponse getAll(){
        return this.unitService.getAllUnit();
    }
}
