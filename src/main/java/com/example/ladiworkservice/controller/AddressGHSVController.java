package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.*;
import com.example.ladiworkservice.repository.AddressRepository;
import com.example.ladiworkservice.service.AddressService;
import com.example.ladiworkservice.controller.reponse.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@CrossOrigin
public class AddressGHSVController {
    private final AddressService addressService;
    Logger logger = LoggerFactory.getLogger(AddressGHSVController.class);

    @Autowired
    AddressRepository addressRepository;
  

    public AddressGHSVController(AddressService addressService) {
        this.addressService = addressService;
    }

//    @GetMapping("/provinces")
//    public ResponseEntity<BaseResponse> getProvince() {
//        BaseResponse response;
//
//        try {
//            response = addressService.getProvince();
//        }
//        catch (RuntimeException ex) {
//            logger.error(String.format("AddressGHSVController|getProvince|Error: %s", ex.getMessage()));
//            throw new RuntimeException(ex);
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/districts/{provinceCode}")
//    public ResponseEntity<BaseResponse> getDistrict(@PathVariable(name = "provinceCode") Long provinceCode){
//        BaseResponse response;
//
//        try {
//            response = addressService.getDistrict(provinceCode);
//        }
//        catch (RuntimeException ex) {
//            logger.error(String.format("AddressGHSVController|getDistrict|Error: %s", ex.getMessage()));
//            throw new RuntimeException(ex);
//        }
//
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/wards/{districtCode}")
//    public ResponseEntity<BaseResponse> getWard(@PathVariable(name = "districtCode") Long districtCode) {
//        BaseResponse response;
//
//        try {
//            response = addressService.getCommune(districtCode);
//        }
//        catch (RuntimeException ex) {
//            logger.error(String.format("AddressGHSVController|getDistrict|Error: %s", ex.getMessage()));
//            throw new RuntimeException(ex);
//        }
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/provinces")
    public BaseResponse provinces(){
        return new BaseResponse(200, "Ok", addressRepository.findProvinces());
    }

    @GetMapping("/districts")
    public BaseResponse districts(@RequestParam String provinceId){
        return new BaseResponse(200, "Ok", addressRepository.findDistinctByProvinceId(Long.parseLong(provinceId)));
    }
    @GetMapping("wards")
    public BaseResponse wards(@RequestParam String provinceId, @RequestParam String districtId){
        return new BaseResponse(200, "Ok", addressRepository.findCommuneByProvinceIdAndDistrictId(Long.parseLong(provinceId), Long.parseLong(districtId)));
    }

    @GetMapping()
    public BaseResponse address(@RequestParam String provinceId, @RequestParam String districtId, @RequestParam String communeId){
        return new BaseResponse(200, "Ok", addressRepository.findAllByProvinceIdAndDistrictIdAndCommuneId(Long.parseLong(provinceId), Long.parseLong(districtId), Long.parseLong(communeId)));
    }
}
