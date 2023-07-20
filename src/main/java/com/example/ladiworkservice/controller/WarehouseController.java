package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateWarehouseRequest;
import com.example.ladiworkservice.repository.WarehouseRepository;
import com.example.ladiworkservice.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse")
@CrossOrigin
@PreAuthorize("hasAuthority('admin')")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @Autowired
    WarehouseRepository warehouseRepository;

    @PostMapping("create")
    public BaseResponse create(@RequestBody CreateWarehouseRequest createWarehouseRequest){
        return warehouseService.create(createWarehouseRequest);
    }

    @GetMapping("getAll")
    public BaseResponse getAll(){
        return new BaseResponse(200, "Ok", warehouseRepository.findAll());
    }

    @DeleteMapping("delete")
    public BaseResponse delete(@RequestParam Long id){
        return warehouseService.deleteById(id);
    }

    @GetMapping("search")
    public BaseResponse search(@RequestParam String filter, @RequestParam String sort, @RequestParam int size, @RequestParam int page){
        return warehouseService.search(filter, sort, size, page);
    }

    @GetMapping("stc-warehouse")
    public BaseResponse statisticWarehouse(@RequestParam String warehouseId){
        return warehouseService.statisticWarehouse(Long.parseLong(warehouseId));
    }


}
