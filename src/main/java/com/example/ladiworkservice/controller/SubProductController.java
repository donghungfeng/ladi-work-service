package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.SubProduct;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.SubProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/sub-product")
@CrossOrigin
public class SubProductController extends BaseController<SubProduct>{

    @Autowired
    SubProductService subProductService;
    @Override
    protected BaseService<SubProduct> getService() {
        return subProductService;
    }
    @Override
    public BaseResponse getAll(){
        return this.getService().getAll();
    }

    @Override
    public BaseResponse create(@RequestBody SubProduct subProduct) throws NoSuchAlgorithmException, IOException {
        return new BaseResponse(201, "message", "Không thực hiện chức năng gì");
    }

    @Override
    public BaseResponse update(Long id,@RequestBody SubProduct subProduct){
        return new BaseResponse(201, "message", "Không thực hiện chức năng gì");
    }

    @Override
    public BaseResponse delete(@RequestParam(name = "id") Long id){
        return new BaseResponse(201, "message", "Không thực hiện chức năng gì");
    }
}
