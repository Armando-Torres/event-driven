package com.eventdriven.producer.order.application;

import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Status;

import lombok.AllArgsConstructor;

import com.eventdriven.producer.order.application.service.OrderResponse;

@Component
@AllArgsConstructor
public class DeleteOrderLine {
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
