package com.eventdriven.producer.order.domain;

import java.util.List;

import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.LineItem;
import com.eventdriven.producer.order.domain.vo.Status;

import lombok.Data;
import lombok.NonNull;

@Data
public class Order {
    private Long id;

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
