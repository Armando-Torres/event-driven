package com.eventdriven.producer.Order.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.Address;

@Component
public class UpdateOrderAddress {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId, Address newAddress) {        
        Order order = this.orderRepository.findById(orderId);

        order.setAddress(newAddress);
        this.orderRepository.editAddress(order.getId(), newAddress);
        
        return new OrderResponse(order);
    }
}
