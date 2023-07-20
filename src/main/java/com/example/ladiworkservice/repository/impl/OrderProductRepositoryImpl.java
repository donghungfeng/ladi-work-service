package com.example.ladiworkservice.repository.impl;

import com.example.ladiworkservice.repository.OrderProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {

    private final EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(OrderProductRepositoryImpl.class);

    public OrderProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public int createOrderProduct(Long orderId, Long productId) {
        int orderProductInsertSuccess = 0;

        var query = String.format("INSERT INTO order_product (order_id, product_id) VALUES (1,2)", orderId, productId);
        try {
            orderProductInsertSuccess = entityManager
                    .createQuery(query)
                    .setParameter(1, orderId)
                    .setParameter(2, productId)
                    .executeUpdate();
        }
        catch (Exception ex)
        {
            logger.error(String.format("OrderProductRepositoryImpl|createOrderProduct|Error: %s", ex.getMessage()));
            throw new RuntimeException(ex.getMessage());
        }

        return orderProductInsertSuccess;
    }
}
