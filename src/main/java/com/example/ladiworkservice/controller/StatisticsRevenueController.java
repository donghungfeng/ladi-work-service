package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.service.StatisticsRevenueService;
import com.example.ladiworkservice.service.impl.StatisticsRevenueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics-revenue")
@CrossOrigin
public class StatisticsRevenueController{
    @Autowired
    StatisticsRevenueService statisticsRevenueService;
    @Autowired
    StatisticsRevenueServiceImpl statisticsRevenueServiceImpl;

    @GetMapping("/statistic")
    private BaseResponse statistic(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam Long shop){
        return statisticsRevenueService.statisticRevenue(startDate, endDate, shop);
    }

    @GetMapping("/search")
    private BaseResponse search(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam Long shop){
        return statisticsRevenueService.search(startDate, endDate, shop);
    }

    @GetMapping("test-cronjob")
    private BaseResponse testCronjob(){
        statisticsRevenueServiceImpl.statisticRevenueCronjob();
        return new BaseResponse().success("Ok");
    }

}
