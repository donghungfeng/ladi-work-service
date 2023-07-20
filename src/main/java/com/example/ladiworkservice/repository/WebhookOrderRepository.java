package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.WebhookOrder;

import java.util.List;

public interface WebhookOrderRepository extends BaseRepository<WebhookOrder> {

    List<WebhookOrder> getByOrderCodeIn(List<String> orderCodes);
}
