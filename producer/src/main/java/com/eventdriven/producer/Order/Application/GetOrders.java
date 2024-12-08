package com.eventdriven.producer.Order.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.OrderCriteria;

@Component
public class GetOrders {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderResponse> invoke(String address) {
        OrderCriteria criteria = new OrderCriteria(new Address(address, null));

        List<Order> orderList = this.orderRepository.findAll(criteria);
        
        return orderList.stream().map(order -> new OrderResponse(order)).toList();
    }
}
