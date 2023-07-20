package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.SaleKpiStatisticRequest;
import com.example.ladiworkservice.service.SaleKpiStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SalePerformance")
@CrossOrigin
public class SaleKpiStatisticController {
    @Autowired
    private SaleKpiStatisticService saleKpiStatisticService;

    @GetMapping("/SalerKpiStatistic")
    BaseResponse getSalerKpiStatistics(SaleKpiStatisticRequest req)  {
        return  saleKpiStatisticService.getSalerKpiStatistics(req);
    }
}
