package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.UtmMedium;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.UtmMediumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/utmmedium")
@CrossOrigin
public class UtmMediumController extends BaseController<UtmMedium> {

    @Autowired
    UtmMediumService utmMediumService;
    @Override
    protected BaseService<UtmMedium> getService() {
        return utmMediumService;
    }

    @GetMapping("")
    public BaseResponse getAllData(@RequestHeader(name = "Authorization") String jwt){
        return new BaseResponse(0,"Lấy dữ liệu thành công",utmMediumService.getAllData(jwt));
    }


}
