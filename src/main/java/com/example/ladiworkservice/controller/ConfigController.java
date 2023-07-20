package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.FindConfigByCODERequest;
import com.example.ladiworkservice.model.Config;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("config")
@CrossOrigin
public class ConfigController extends BaseController<Config> {
    @Autowired
    ConfigService configService;

    @Override
    protected BaseService<Config> getService() {
        return configService;
    }

    @PostMapping("/getByCODE")
    public BaseResponse getByCODE(@RequestBody FindConfigByCODERequest findConfigByCODERequest) {
        return configService.getByCODE(findConfigByCODERequest);
    }

    @GetMapping("getconfigbydate")
    public BaseResponse getConfigByDate(@RequestParam String startDate, @RequestParam String endDate) {
        return configService.getConfigByDate(startDate, endDate);
    }
}
