package com.example.ladiworkservice.repository;

public interface OrderProductRepository {
    int createOrderProduct(Long orderId, Long productId);
}
