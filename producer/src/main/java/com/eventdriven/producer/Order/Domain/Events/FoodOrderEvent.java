package com.eventdriven.producer.Order.Domain.Events;

import java.util.Date;

import com.eventdriven.producer.Order.Domain.Order;

import lombok.Getter;

@Getter
public class FoodOrderEvent {
    private final String eventType = "food-order";

    private Date timestamp;

    private Order data;

    public FoodOrderEvent (Order order) {
        this.timestamp = new Date();
        this.data = order;
    }
}
