package com.eventdriven.producer.order.domain.events;

import java.util.Date;

import com.eventdriven.producer.order.domain.Order;

import lombok.Getter;

@Getter
public class FoodOrderEvent {
    private String eventType = "food-order";

    private Date timestamp;

    private Order data;

    public FoodOrderEvent (Order order) {
        this.timestamp = new Date();
        this.data = order;
    }
}
