package com.example.ladiworkservice.service.impl;

import com.example.ladiworkservice.model.WebhookOrder;
import com.example.ladiworkservice.repository.WebhookOrderRepository;
import com.example.ladiworkservice.service.WebhookOrderService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WebhookOrderServiceImpl implements WebhookOrderService {

    private final WebhookOrderRepository webhookOrderRepository;

    public WebhookOrderServiceImpl(WebhookOrderRepository webhookOrderRepository) {
        this.webhookOrderRepository = webhookOrderRepository;
    }

    @Override
    public List<WebhookOrder> getByOrderCodes(List<String> orderCodes) {
        return webhookOrderRepository.getByOrderCodeIn(orderCodes);
    }

    @Override
    public void saveAll(List<WebhookOrder> webhookOrders) {
        webhookOrderRepository.saveAll(webhookOrders);
    }
}
