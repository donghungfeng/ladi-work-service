package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.BoLDetail;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.BoLDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/bol-detail")
@CrossOrigin
public class BoLDetailController extends BaseController<BoLDetail> {
    @Autowired
    BoLDetailService boLDetailService;
    @Override
    protected BaseService<BoLDetail> getService() {
        return boLDetailService;
    }

    @GetMapping("getAll")
    public BaseResponse getAll(){
        return this.getService().getAll();
    }

    @Override
    public BaseResponse create(@RequestBody BoLDetail t) throws NoSuchAlgorithmException, IOException {
        return new BaseResponse(200, "Ok", "Không thực hiện chức năng gì");
    }

    @PutMapping("update")
    public BaseResponse update(@RequestParam Long id,@RequestBody BoLDetail t) throws NoSuchAlgorithmException {
        return new BaseResponse(200, "Ok", "Không thực hiện chức năng gì");
    }

    @GetMapping("details")
    public BaseResponse details(@RequestParam(name = "id") Long id){
        return new BaseResponse(200, "Ok", "Không thực hiện chức năng gì");
    }

    @DeleteMapping("delete")
    public BaseResponse delete(@RequestParam(name = "id") Long id){
        return new BaseResponse(200, "Ok", "Không thực hiện chức năng gì");
    }
}
