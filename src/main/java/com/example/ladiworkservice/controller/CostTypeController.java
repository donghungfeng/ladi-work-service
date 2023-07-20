package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.CostType;
import com.example.ladiworkservice.repository.CostTypeRepository;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.CostTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("costtype")
@CrossOrigin
@PreAuthorize("hasAuthority('admin')")
public class CostTypeController extends BaseController<CostType>{
    @Autowired
    CostTypeRepository costTypeRepository;
    CostTypeService costTypeService;
    public CostTypeController(CostTypeService costTypeServicee){
        this.costTypeService = costTypeServicee;
    }
    @Override
    protected BaseService<CostType> getService() {
        return this.costTypeService;
    }
    @Override
    public BaseResponse create(@RequestBody CostType costType) throws JsonProcessingException {
        if(costTypeRepository.findByCode(costType.getCode())!=null){
            return new BaseResponse(500, "Loại chi phí đã tồn tại",null );
        }

        return new BaseResponse(200, "Tạo thành công!", this.getService().create(costType));
    }
}
