package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.PostCostRequest;
import com.example.ladiworkservice.model.Cost;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.CostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cost")
@CrossOrigin
public class CostController extends BaseController<Cost>{
    CostService costService;
    public CostController(CostService costService){
        this.costService = costService;
    }
    @Override
    protected BaseService<Cost> getService() {
        return this.costService;
    }

    @PostMapping("/postcost")
    public BaseResponse postCost(@RequestBody PostCostRequest postCostRequest){
        return costService.postCost(postCostRequest);
    }

    @GetMapping("/getCost")
    public BaseResponse getCost(){
        return costService.getCost();
    }

    @GetMapping("/laysodontheothoigian")
    public BaseResponse laySoDonTheoThoiGian(@RequestParam String startDate, @RequestParam String endDate){
        return costService.laySoDonTheoThoiGian(startDate, endDate);
    }

    @GetMapping("/getallcostbytimerange")
    public BaseResponse getAllCostByTimeRange(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode, @RequestHeader(name = "Authorization") String jwt){
        return costService.getAllCostByTimeRange(startDate, endDate, shopCode ,jwt);
    }

    @GetMapping("/test")
    public BaseResponse test(){
        costService.createCostTransport();
        return costService.createCostTransport();
    }
}
