package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.Unit;
import com.example.ladiworkservice.repository.UnitRepository;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.UnitService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("unit")
@CrossOrigin
public class UnitController extends BaseController<Unit>{
   @Autowired
    UnitService unitService;
    @Autowired
    UnitRepository unitRepository;
    @Override
    protected BaseService<Unit> getService() {
        return unitService;
    }
    @Override
    public BaseResponse create(@RequestBody Unit unit) throws JsonProcessingException {
        if(unitRepository.findByCode(unit.getCode())!=null){
            return new BaseResponse(500, "Đơn vị đã tồn tại",null );
        }

        return new BaseResponse(200, "Tạo thành công!", this.getService().create(unit));
    }
}
