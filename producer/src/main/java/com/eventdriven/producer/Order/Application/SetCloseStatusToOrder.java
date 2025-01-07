package com.eventdriven.producer.order.application;

import org.springframework.stereotype.Component;

import com.eventdriven.producer.shared.domain.MessageProducer;
import com.eventdriven.producer.order.application.service.OrderResponse;
import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.events.FoodOrderEvent;
import com.eventdriven.producer.order.domain.vo.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SetCloseStatusToOrder {
    private static final String TOPIC_NAME = "order-events";

    private OrderRepository orderRepository;
    private MessageProducer messageProducer;

    public OrderResponse invoke(Long orderId) {        
        Order order = this.orderRepository.findById(orderId);

        this.checkIfOrderIsClosed(order);

        order.setStatus(Status.CLOSED);

        Order newOrder = this.orderRepository.save(order);

        this.sendOrderCloseEvent(order);
        
        return new OrderResponse(newOrder);
    }

    private void checkIfOrderIsClosed(Order order) {
        if (order.getStatus() == Status.CLOSED) {
            throw new IllegalStateException(String.format("The order with id %d is CLOSED", order.getId()));
        }
    }

    private void sendOrderCloseEvent(Order order) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            FoodOrderEvent event = new FoodOrderEvent(order);
            this.messageProducer.sendMessage(TOPIC_NAME, order.getId().toString(), objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            
        }        
    }
}
