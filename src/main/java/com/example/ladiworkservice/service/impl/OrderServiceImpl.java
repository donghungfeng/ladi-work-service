package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.model.Order;
import com.example.ladiworkservice.repository.BaseRepository;
import com.example.ladiworkservice.repository.OrderReponsitory;
import com.example.ladiworkservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {
    @Autowired
    OrderReponsitory orderReponsitory;
    @Override
    protected BaseRepository<Order> getRepository() {
        return orderReponsitory;
    }
}
