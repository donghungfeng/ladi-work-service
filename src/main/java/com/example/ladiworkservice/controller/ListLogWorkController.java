package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.model.ListLogWork;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.ListLogWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("list_log_work")
@CrossOrigin
public class ListLogWorkController extends BaseController<ListLogWork> {
    @Autowired
    ListLogWorkService listLogWorkService;

    @Override
    protected BaseService<ListLogWork> getService() { return listLogWorkService; }

    @GetMapping("searchData")
    public BaseResponse findDataByDate(@RequestParam Long startDate, @RequestParam Long endDate, @RequestParam int size, @RequestParam int page) {
        return listLogWorkService.findDataByDate(startDate, endDate, size, page);
    }
}
