package com.eventdriven.producer.Order.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String address;

    private String details;
}
