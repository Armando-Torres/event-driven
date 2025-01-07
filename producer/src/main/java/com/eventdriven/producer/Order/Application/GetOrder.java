package com.eventdriven.producer.order.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.application.service.OrderResponse;

@Component
public class GetOrder {
    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId) {
        Order order = this.orderRepository.findById(orderId);
        
        return new OrderResponse(order);
    }
}
