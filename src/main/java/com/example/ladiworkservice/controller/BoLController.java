package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.CreateBoLRequest;
import com.example.ladiworkservice.repository.BoLDetailRepository;
import com.example.ladiworkservice.service.BoLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bol")
@CrossOrigin
public class BoLController{
    @Autowired
    BoLService boLService;

    @Autowired
    BoLDetailRepository boLDetailRepository;

    @PostMapping()
    @PreAuthorize("hasAuthority('admin')")
    public BaseResponse create(@RequestBody CreateBoLRequest createBoLRequest){
        return boLService.create(createBoLRequest);
    }

    @GetMapping("/search")
    public BaseResponse search(@RequestParam String filter, @RequestParam String sort, @RequestParam int size, @RequestParam int page){
        return boLService.search(filter, sort, size, page);
    }

    @GetMapping("/stc-bol-by-status")
    public BaseResponse statisticBolByStatus(@RequestParam int type, @RequestParam String shopCode){
        return boLService.statisticBoLByStatus(type, shopCode);
    }

    @GetMapping("/stc-bol")
    public BaseResponse statisticBoL(@RequestParam int type, @RequestParam String shopId) {
        return boLService.statisticBoL(type, Long.parseLong(shopId));
//        return new BaseResponse(200, "Ok" , "test");
    }

    @GetMapping("/stc-history-bol")
    public BaseResponse statisticHistoryBoL(@RequestParam String shopCode){
        return boLService.statisticHistoryBoL(shopCode);
    }
}
