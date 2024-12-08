package com.eventdriven.producer.Order.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;

@Component
public class GetOrder {
    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId) {
        Order order = this.orderRepository.findById(orderId);
        
        return new OrderResponse(order);
    }
}
