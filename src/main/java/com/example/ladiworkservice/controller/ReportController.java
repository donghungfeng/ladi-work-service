package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.MarketingPerformanceReq;
import com.example.ladiworkservice.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@CrossOrigin
public class ReportController {

    @Autowired
    private DataService dataService;

    @GetMapping("/marketing-performance")
    ResponseEntity getMarketingPerformanceReport(MarketingPerformanceReq req) {
        return ResponseEntity.ok(new BaseResponse().success(
                dataService.getMarketingPerformanceReport(req))
        );
    }
}
