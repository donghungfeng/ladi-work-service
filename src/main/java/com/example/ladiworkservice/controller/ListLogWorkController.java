package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.ListLogWork;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.ListLogWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("list_log_work")
@CrossOrigin
public class ListLogWorkController extends BaseController<ListLogWork> {
    @Autowired
    ListLogWorkService listLogWorkService;

    @Override
    protected BaseService<ListLogWork> getService() { return listLogWorkService; }
}
