package com.eventdriven.producer.order.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.OrderCriteria;

import lombok.AllArgsConstructor;

import com.eventdriven.producer.order.application.service.OrderResponse;

@Component
@AllArgsConstructor
public class GetOrders {
    private OrderRepository orderRepository;

    public List<OrderResponse> invoke(String address) {
        OrderCriteria criteria = new OrderCriteria(new Address(address, null));

        List<Order> orderList = this.orderRepository.findAll(criteria);
        
        return orderList.stream().map(order -> new OrderResponse(order)).toList();
    }
}
