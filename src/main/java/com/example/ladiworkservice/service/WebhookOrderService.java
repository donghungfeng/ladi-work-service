package com.example.ladiworkservice.service;

import com.example.ladiworkservice.model.WebhookOrder;

import java.util.List;

public interface WebhookOrderService {
    List<WebhookOrder> getByOrderCodes(List<String> orderCodes);

    void saveAll(List<WebhookOrder> webhookOrders);
}
