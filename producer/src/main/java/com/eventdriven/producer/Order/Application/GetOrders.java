package com.eventdriven.producer.order.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.OrderCriteria;
import com.eventdriven.producer.order.application.service.OrderResponse;

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
