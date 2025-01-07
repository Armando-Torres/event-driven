package com.eventdriven.producer.order.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String address;

    private String details;
}
