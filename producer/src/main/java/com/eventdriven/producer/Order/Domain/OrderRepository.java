package com.eventdriven.producer.Order.Domain;

import java.util.List;

import com.eventdriven.producer.Order.Domain.ValueObject.OrderCriteria;

public interface OrderRepository {
    public Order save(Order order);

    public Order findById(Long orderId);

    public List<Order> findAll(OrderCriteria criteria);
}
