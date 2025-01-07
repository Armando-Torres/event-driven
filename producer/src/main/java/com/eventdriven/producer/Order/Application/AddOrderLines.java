package com.eventdriven.producer.order.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.application.service.OrderResponse;
import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.LineItem;
import com.eventdriven.producer.order.domain.vo.Status;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AddOrderLines {

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
