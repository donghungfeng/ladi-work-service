package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.model.Order;
import com.example.ladiworkservice.service.BaseService;
import com.example.ladiworkservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController extends BaseController<Order>{
    @Autowired
    OrderService orderService;
    @Override
    protected BaseService<Order> getService() {
        return orderService;
    }
}
