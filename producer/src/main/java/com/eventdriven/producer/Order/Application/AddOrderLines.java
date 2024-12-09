package com.eventdriven.producer.Order.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Order.Domain.ValueObject.Status;

@Component
public class AddOrderLines {

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse invoke(Long orderId, List<LineItem> linesItems) {
        Order order = this.orderRepository.findById(orderId);

        this.checkIfOrderIsOpen(order);

        linesItems.stream().forEach(line -> order.getItems().add(line));

        Order newOrder = this.orderRepository.save(order);

        return new OrderResponse(newOrder);
    }

    private void checkIfOrderIsOpen(Order order) {
        if (order.getStatus() != Status.OPEN) {
            throw new IllegalStateException(String.format("The order with id %d is CLOSED", order.getId()));
        }
    }
}
