package com.eventdriven.producer.order.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.Status;
import com.eventdriven.producer.order.application.service.OrderResponse;

@Component
public class UpdateOrderAddress {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId, Address newAddress) {        
        Order order = this.orderRepository.findById(orderId);

        this.checkIfOrderIsOpen(order);

        order.setAddress(newAddress);

        Order newOrder = this.orderRepository.save(order);
        
        return new OrderResponse(newOrder);
    }

    private void checkIfOrderIsOpen(Order order) {
        if (order.getStatus() != Status.OPEN) {
            throw new IllegalStateException(String.format("The order with id {%d} is CLOSED", order.getId()));
        }
    }
}
