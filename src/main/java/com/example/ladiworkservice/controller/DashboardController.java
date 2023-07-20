package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.RefundRate;
import com.example.ladiworkservice.service.DashboardService;
import com.example.ladiworkservice.service.RefundRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dashboard")
@CrossOrigin
public class DashboardController {

    private final DashboardService dashboardService;
    private final RefundRateService refundRateService;

    public DashboardController(DashboardService dashboardService, RefundRateService refundRateService) {
        this.dashboardService = dashboardService;
        this.refundRateService = refundRateService;
    }

    @GetMapping("/data")
    public ResponseEntity<BaseResponse> getDashboard(@RequestParam String shopCode, @RequestParam Long fromDate, @RequestParam Long toDate) {
        BaseResponse response;

        try {
            response = dashboardService.getDataDashboard(shopCode, fromDate, toDate);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/revenueStatistics")
    public ResponseEntity<BaseResponse> getRevenueStatistics(@RequestParam String shopCode, @RequestParam Long fromDate, @RequestParam Long toDate) {
        BaseResponse response;

        try {
            response = dashboardService.getDataRevenueStatistics(shopCode, fromDate, toDate);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/marketing")
    public ResponseEntity<BaseResponse> getMarketingUtm(@RequestParam String shopCode, @RequestParam Long fromDate, @RequestParam Long toDate) {
        BaseResponse response;

        try {
            response = dashboardService.getMarketingUtmDto(shopCode, fromDate, toDate);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sale")
    public ResponseEntity<BaseResponse> getSaleUtm(@RequestParam String shopCode, @RequestParam Long fromDate, @RequestParam Long toDate) {
        BaseResponse response;

        try {
            response = dashboardService.getSaleUtmDto(shopCode, fromDate, toDate);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

//    @GetMapping("/productWarehouse")
//    public ResponseEntity<BaseResponse> getProductWarehouse(@RequestParam String shopCode, @RequestParam Long fromDate, @RequestParam Long toDate) {
//        BaseResponse response;
//
//        try {
//            response = dashboardService.getCostMarketingGroupByName(shopCode, fromDate, toDate);
//        }
//        catch (Exception ex) {
//            throw new RuntimeException(ex.getMessage());
//        }
//
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("refundRate")
    public ResponseEntity<BaseResponse> InUp(@RequestBody RefundRate refundRate) {
        BaseResponse response;

        try {
            response = refundRateService.inupRefundRate(refundRate);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("refundRate")
    public ResponseEntity<BaseResponse> getRefundRateById(@RequestParam Long id) {
        BaseResponse response = new BaseResponse(200, "Thành công", null);

        try {
            response.RESULT = refundRateService.findById(id);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("productWarehouse")
    public ResponseEntity<BaseResponse> getProductWarehouse(@RequestParam Long fromDate, @RequestParam Long toDate) {
        BaseResponse response = new BaseResponse(200, "Thành công", null);

        try {
            response.RESULT = dashboardService.getProductWareHouseChartDto(fromDate, toDate);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        return ResponseEntity.ok(response);
    }


}
