package com.eventdriven.producer.Order.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.Status;

@Component
public class DeleteOrderLine {
    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId, Integer listItemPosition) {
        Order order = this.orderRepository.findById(orderId);

        this.checkIfOrderIsOpen(order);

        order.getItems().remove(listItemPosition.intValue());

        Order newOrder = this.orderRepository.save(order);

        return new OrderResponse(newOrder);
    }

    private void checkIfOrderIsOpen(Order order) {
        if (order.getStatus() != Status.OPEN) {
            throw new IllegalStateException(String.format("The order with id %d is CLOSED", order.getId()));
        }
    }
}
