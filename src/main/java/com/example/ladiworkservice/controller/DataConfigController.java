package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.DataConfig;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.DataConfigService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dataconfig")
@CrossOrigin
public class DataConfigController extends BaseController<DataConfig> {

    private final DataConfigService dataConfigService;

    public DataConfigController(DataConfigService dataConfigService) {
        this.dataConfigService = dataConfigService;
    }

    @Override
    protected BaseService<DataConfig> getService() {
        return dataConfigService;
    }
}
