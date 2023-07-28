package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.controller.request.Log_workRequest;
import com.example.ladiworkservice.model.Log_work;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.Log_workService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("log_work")
@CrossOrigin
public class LogWorkController extends BaseController<Log_work>{
    @Autowired
    Log_workService logWorkService;
    @Override
    protected BaseService<Log_work> getService() {
        return logWorkService;
    }
    @PostMapping("checkIn")
    public BaseResponse checkIn(@RequestBody Log_workRequest logWorkRequest)
    {
        return logWorkService.checkIn(logWorkRequest);
    }
    @GetMapping("searchByUser")
    public BaseResponse findLogWorkByUser(@RequestParam String code,@RequestParam Long unitId,@RequestParam Long startDate,@RequestParam Long endDate , @RequestParam int size, @RequestParam int page)
    {
        return logWorkService.findLogWorkByUser(code, unitId,startDate, endDate, size, page);
    }
}
