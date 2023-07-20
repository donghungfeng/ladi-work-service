package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.AssignJobRequest;
import com.example.ladiworkservice.controller.request.UpdateStatusDataListRequest;
import com.example.ladiworkservice.model.OrderGHN;
import com.example.ladiworkservice.repository.OrderGHNRepository;
import com.example.ladiworkservice.repository.OrderGHSVRepository;
import com.example.ladiworkservice.shippingorders.ghn.request.UpdateStatusDataWebhookGHNRQ;
import com.example.ladiworkservice.shippingorders.ghsv.reponse.UpdateStatusDataWebhookGHSVRP;
import com.example.ladiworkservice.shippingorders.ghsv.request.UpdateStatusDataWebhookGHSVRQ;
import com.example.ladiworkservice.model.Data;
import com.example.ladiworkservice.repository.CustomDataRepository;
import com.example.ladiworkservice.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/data")
@CrossOrigin
public class DataController {

    @Autowired
    DataService dataService;

    @Autowired
    CustomDataRepository customDataRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderGHNRepository orderGHNRepository;

    @Autowired
    OrderGHSVRepository orderGHSVRepository;


    @GetMapping("")
    public BaseResponse getAllData(@RequestHeader(name = "Authorization") String jwt, @RequestParam String status, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int page, @RequestParam int size, @RequestParam(name = "shopCode", required = false) String shopCode) {

        return dataService.getAllData(jwt, status, startDate, endDate, shopCode, page, size);
    }

    @PostMapping("createData")
    public BaseResponse createData(@RequestBody Data data, @RequestParam(name = "shopCode", required = false) String shopCode) {
        return dataService.createData(data, shopCode);
    }

    @PostMapping("/create/v2")
    public BaseResponse createDataV2(@RequestBody Object data, @RequestParam(name = "shopCode", required = false) String shopCode) throws JsonProcessingException {
        return new BaseResponse(200, "OK", dataService.createV2(data, shopCode));
    }

    @PostMapping("assignWork")
    public BaseResponse assignWork(@RequestHeader(name = "Authorization") String jwt, @RequestBody AssignJobRequest assignJobRequest) throws JsonProcessingException, NoSuchAlgorithmException {
        return dataService.assignWork(jwt, assignJobRequest);
    }

    @GetMapping("getAllDataAccountNull")
    public BaseResponse getAllBaseResponse(@RequestParam String status, @RequestParam(name = "shopCode", required = false) String shopCode) {
        return dataService.getAllDataAccountNull(status, shopCode);
    }

    @GetMapping("statisticByUtmMedium")
    public BaseResponse statisticByUtmMedium() {
        return dataService.statisticByUtmMedium();
    }

    @GetMapping("statisticalRevenueByDay")
    public BaseResponse statisticalRevenueByDay() {
        return dataService.statisticcalrevenueByDay();
    }

    @GetMapping("thongKeUtm")
    public BaseResponse thongKeUtm(@RequestParam String startDate, @RequestParam String endDate, @RequestHeader(name = "Authorization") String jwt) {
        return dataService.ketQuaThongKeUtm(startDate, endDate, jwt);
    }

    @GetMapping("thongKeTopUtm")
    public BaseResponse thongKeTopUtm(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode) {
        return dataService.ketQuaThongKeTopUtm(startDate, endDate, shopCode);
    }

    @GetMapping("thongkedoanhthutheongay")
    public BaseResponse statisticalRevenueByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode) {
        return dataService.statisticalRevenueByDate(startDate, endDate, shopCode);
    }

    @GetMapping("thongkeutm")
    public BaseResponse statisticUtmByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode) {
        return dataService.statisticUtmByDate(startDate, endDate, shopCode);
    }

    @GetMapping("statisticdatabydateandstatus")
    public BaseResponse statisticDataByDateAndStatus(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode) {
        return dataService.statisticDataByDateAndStatus(startDate, endDate, shopCode);
    }

    @GetMapping("statisticdatabydateandstatusanduser")
    public BaseResponse statisticDataByDateAndStatusAndUser(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode, @RequestParam String userId) {
        return dataService.statisticDataByDateAndStatusAndUser(startDate, endDate, shopCode, userId);
    }

    @GetMapping("stcquantity")
    public BaseResponse statisticQuantityDataByDateAndStatus(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode) {
        return dataService.statisticQuantityDataByDateAndStatus(startDate, endDate, shopCode);
    }

    @GetMapping("get-by-phone")
    public BaseResponse getAllByPhone(@RequestParam String phone, @RequestParam String shopCode) {
        return dataService.getAllByPhone(phone, shopCode);
    }

    @GetMapping("update-cost")
    public BaseResponse updateCost(@RequestParam Double cost) {
        return dataService.resetCost(cost);
    }

    @GetMapping("unique-shop")
    public BaseResponse getUniqueShop(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String id) {
        return dataService.findUniqueShop(startDate, endDate, Long.parseLong(id));
    }

    @GetMapping("stc-cost-data")
    public BaseResponse statisticCostByStatusData(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String shopCode) {
        return dataService.statisticCostByStatusData(startDate, endDate, shopCode);
    }

    @GetMapping("search")
    public BaseResponse search(@RequestParam String filter, @RequestParam String sort, @RequestParam int size, @RequestParam int page, @RequestHeader(name = "Authorization") String jwt) {
        return dataService.searchByRole(filter, sort, size, page, jwt);
    }

    @GetMapping("getAll")
    public BaseResponse getAll() {
        return dataService.getAll();
    }

    @PostMapping("create")
    public BaseResponse create(@RequestBody Data t) throws NoSuchAlgorithmException, IOException {
        return dataService.create(t);
    }

    @PutMapping("update")
    public BaseResponse update(@RequestParam Long id, @RequestBody Data t) throws NoSuchAlgorithmException, JsonProcessingException {
        return dataService.update(id, t);
    }

    @GetMapping("details")
    public BaseResponse details(@RequestParam(name = "id") Long id) {
        return dataService.getById(id);
    }

    @DeleteMapping("delete")
    public BaseResponse delete(@RequestParam(name = "id") Long id) throws JsonProcessingException {
        return dataService.deleteById(id);
    }

    @PostMapping("updateStatusDataList")
    public BaseResponse updateStatusDataList(@RequestBody UpdateStatusDataListRequest updateStatusDataListRequest) {
        return dataService.updateStatusDataList(updateStatusDataListRequest.getDataList(), updateStatusDataListRequest.getStatus());
    }

    @PostMapping("update-status-data/ghsv")
    public UpdateStatusDataWebhookGHSVRP updateStatusDataGHSV(@RequestBody @Valid UpdateStatusDataWebhookGHSVRQ body) throws JsonProcessingException, NoSuchAlgorithmException {
        return dataService.updateStatusGHSV(body);
    }

    @PostMapping("update-status-data/ghn")
    public UpdateStatusDataWebhookGHNRQ updateStatusDataGHN(@RequestBody @Valid UpdateStatusDataWebhookGHNRQ body) {
        OrderGHN orderGHN = modelMapper.map(body, OrderGHN.class);
        orderGHNRepository.save(orderGHN);
        return body;
    }

    @GetMapping("/statisticRevenue")
    public BaseResponse statisticRevenue(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam String shopCode){
        return dataService.statisticRevenue(startDate, endDate, shopCode);
    }

    @GetMapping("/tracking")
    public BaseResponse tracking(@RequestParam String code){
        return dataService.tracking(code);
    }

}
