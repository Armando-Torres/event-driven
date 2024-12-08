package com.eventdriven.producer.Order.Domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCriteria {
    private Address address;
}
