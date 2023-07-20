package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.Shop;
import com.example.ladiworkservice.service.ShopService;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ladiworkservice.service.BaseService;


@RestController
@RequestMapping("/shop")
@CrossOrigin
public class ShopController extends BaseController<Shop> {
    @Autowired
    ShopService shopService;
    @Override
    protected BaseService<Shop> getService() {
        return shopService;
    }

    @GetMapping()
    public BaseResponse getAllByStatus(@RequestHeader(name = "Authorization") String jwt, @RequestParam(name = "status") int status){
        return shopService.getAllByStatus(status, jwt);
    }
}
