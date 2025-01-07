package com.eventdriven.producer.order.application.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import com.eventdriven.producer.order.domain.Order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderResponse {
    private Order customerOrder;

    public Long getOrderId() {
        return customerOrder.getId();
    }

    public String getAddress() {
        return String.format("%s - %s", customerOrder.getAddress().getAddress(), customerOrder.getAddress().getDetails());
    }

    public List<String> getListItems() {
        return customerOrder.getItems().stream()
            .map(item -> String.format("%s | %d | %.2f | %.2f", item.getDesc(), item.getQuantity(), item.getPrice(), item.getAmount()))
            .collect(Collectors.toList());
    };

    public BigDecimal getNet() {
        return BigDecimal.valueOf(customerOrder.getNet()).setScale(2, RoundingMode.UP);
    }
    
    public BigDecimal getTotal() {
        return BigDecimal.valueOf(customerOrder.getTotal()).setScale(2,  RoundingMode.UP);
    }

    public BigDecimal getVat() {
        return  BigDecimal.valueOf(customerOrder.getVat()).setScale(2,  RoundingMode.UP);
    }
}
