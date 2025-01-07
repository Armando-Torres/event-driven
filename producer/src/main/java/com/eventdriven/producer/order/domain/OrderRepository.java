package com.eventdriven.producer.order.domain;

import java.util.List;

import com.eventdriven.producer.order.domain.vo.OrderCriteria;

public interface OrderRepository {
    public Order save(Order order);

    public Order findById(Long orderId);

    public List<Order> findAll(OrderCriteria criteria);
}
