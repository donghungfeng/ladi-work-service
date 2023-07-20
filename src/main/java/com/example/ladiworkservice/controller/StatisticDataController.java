package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.service.StatisticDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic/data")
@CrossOrigin
public class StatisticDataController {

    @Autowired
    StatisticDataService statisticDataService;

    @GetMapping("/revenue")
    public BaseResponse revenue(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam  String shopCode){
        return statisticDataService.revenue(startDate, endDate, shopCode);
    }
}
