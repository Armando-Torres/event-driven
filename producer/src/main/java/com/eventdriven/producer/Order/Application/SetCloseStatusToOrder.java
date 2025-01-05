package com.eventdriven.producer.Order.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.Events.FoodOrderEvent;
import com.eventdriven.producer.Order.Domain.ValueObject.Status;
import com.eventdriven.producer.Shared.Domain.MessageProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SetCloseStatusToOrder {
    private final String topicName = "order-events";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
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
            this.messageProducer.sendMessage(this.topicName, order.getId().toString(), objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            
        }        
    }
}
