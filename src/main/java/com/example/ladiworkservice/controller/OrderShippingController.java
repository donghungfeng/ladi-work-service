package com.example.ladiworkservice.controller;

import com.example.ladiworkservice.controller.reponse.BaseResponse;
import com.example.ladiworkservice.service.*;
import com.example.ladiworkservice.shippingorders.ghn.request.ShippingOrdersRequest;
import com.example.ladiworkservice.repository.AccountRepository;
import com.example.ladiworkservice.repository.SubProductRepository;
import com.example.ladiworkservice.service.*;
import com.example.ladiworkservice.model.OrderShipping;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order-shipping")
@CrossOrigin
public class OrderShippingController extends BaseController<OrderShipping>{
    private final RestTemplate restTemplate;
    private final OrderShippingService orderService;
    private final ConfigService configService;
    private final AccountShippingService accountShippingService;
    private final DataService dataService;

    Logger logger = LoggerFactory.getLogger(OrderShippingController.class);

    public OrderShippingController(OrderShippingService orderService, RestTemplate restTemplate, ConfigService configService, AccountShippingService accountShippingService, DataService dataService) {
        this.orderService = orderService;
        this.restTemplate = restTemplate;
        this.configService = configService;
        this.accountShippingService = accountShippingService;
        this.dataService = dataService;
    }

    @Autowired
    SubProductRepository subProductRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BoLService boLService;

    @Override
    protected BaseService<OrderShipping> getService() {
        return orderService;
    }

    @PostMapping("shipping-orders")
    public BaseResponse shippingOrders(@RequestBody ShippingOrdersRequest shippingOrdersRequest, @RequestHeader(name = "Authorization") String jwt) throws JsonProcessingException {
        return orderService.shippingOrders(shippingOrdersRequest, jwt);
    }
}
