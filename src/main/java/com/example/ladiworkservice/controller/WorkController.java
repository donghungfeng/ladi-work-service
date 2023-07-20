package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CheckOutRequest;
import com.example.ladiworkservice.controller.request.CheckWorkActiveRequest;
import com.example.ladiworkservice.controller.request.CreateWorkRequest;
import com.example.ladiworkservice.model.Work;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("work")
@CrossOrigin
public class WorkController extends BaseController<Work>{
    @Autowired
    WorkService workService;
    @Override
    protected BaseService<Work> getService() {
        return workService;
    }

    @PostMapping("")
    public BaseResponse createWork(@RequestBody CreateWorkRequest createWorkRequest){
        return workService.createWork(createWorkRequest);
    }

    @GetMapping("")
    public BaseResponse getAllWork(@RequestHeader(name = "Authorization") String jwt, @RequestParam String startDate, @RequestParam String endDate){

        return workService.getAllWork(jwt, startDate, endDate);
    }

    @PostMapping("/checkOut")
    public BaseResponse checkOut(@RequestBody CheckOutRequest checkOutRequest, @RequestParam(name = "shopCode", required = false) String shopCode){
        return workService.checkOut(checkOutRequest, shopCode);
    }

    @GetMapping("getAllActive")
    public BaseResponse getAllIsActive(@RequestParam(name = "shopCode", required = false) String shopCode){
        return workService.getAllActive(shopCode);
    }

    @PostMapping("checkWorkActive")
    public BaseResponse getAllIsActive(@RequestBody CheckWorkActiveRequest checkWorkActiveRequest, @RequestParam(name = "shopCode", required = false) String shopCode){
        return workService.checkWorkActive(checkWorkActiveRequest, shopCode);
    }

    @PostMapping("infoCheckout")
    public BaseResponse infoCheckout(@RequestParam Long id){
//        return new BaseResponse(200, "Ok", "hello");
        return workService.infoCheckout(id);
    }

    @GetMapping("stcproductivity")
    public BaseResponse statisticPerformanceSale(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode){
        return workService.statisticPerformanceSale(startDate, endDate, shopCode);
    }
}
