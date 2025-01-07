package com.eventdriven.producer.order.application;

import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;

import lombok.AllArgsConstructor;

import com.eventdriven.producer.order.application.service.OrderResponse;

@Component
@AllArgsConstructor
public class GetOrder {
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId) {
        Order order = this.orderRepository.findById(orderId);
        
        return new OrderResponse(order);
    }
}
