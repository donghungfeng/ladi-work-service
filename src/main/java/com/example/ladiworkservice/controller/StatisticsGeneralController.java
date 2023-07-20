package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.StatisticsGeneral;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.StatisticsGeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics-general")
@CrossOrigin
public class StatisticsGeneralController extends BaseController<StatisticsGeneral>{
    @Autowired
    StatisticsGeneralService statisticsGeneralService;

    @Override
    protected BaseService<StatisticsGeneral> getService() {
        return statisticsGeneralService;
    }

    @GetMapping("/statistic")
    public BaseResponse statistic(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam Long shop){
        return statisticsGeneralService.statistic(startDate, endDate, shop);
    }

    @GetMapping("/statistic-cost")
    public BaseResponse statisticCost(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam Long shop){
        return statisticsGeneralService.statisticCost(startDate, endDate, shop);
    }
}
