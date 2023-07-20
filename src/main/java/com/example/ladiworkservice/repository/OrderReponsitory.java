package com.example.ladiworkservice.repository;

import com.example.ladiworkservice.model.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderReponsitory extends BaseRepository<Order>{
    Order findAllByOrderCode(String orderCode);

    @Query(value= "select * from webhook_order where date_create <= :endDate and date_create >= :startDate",  nativeQuery = true)
    List<Order> findOrderWithDate(Long startDate, Long endDate);
}
