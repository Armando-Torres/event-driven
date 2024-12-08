package com.eventdriven.producer.Order.Domain;

import java.util.List;

import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Order.Domain.ValueObject.Status;

import lombok.Data;
import lombok.NonNull;

@Data
public class Order {
    private Long Id;

    @NonNull
    private Address address;

    @NonNull
    private List<LineItem> items;

    private Double vat;

    private Status status;

    public Double getTotal() {
        Double net = this.getNet();
        return (net * this.vat) / 100 + net;
    }

    public Double getNet() {
        return this.items.stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
    }
}
