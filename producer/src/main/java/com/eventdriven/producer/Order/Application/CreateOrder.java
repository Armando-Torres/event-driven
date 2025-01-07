package com.eventdriven.producer.order.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.eventdriven.producer.order.application.service.CreateOrderRequest;
import com.eventdriven.producer.order.application.service.OrderResponse;
import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.LineItem;
import com.eventdriven.producer.order.domain.vo.Status;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CreateOrder {

    private OrderRepository orderRepository;

    public OrderResponse invoke(CreateOrderRequest orderData) {
        Address customerAddress = new Address(orderData.getFullAddress(), orderData.getAddressDetail());
        List<LineItem> customerOrderLines = orderData.getOrderLines();
        
        Order customerOrder = new Order(customerAddress, customerOrderLines);

        // NOTICE: Setted for demo purposes only
        customerOrder.setVat(21.10);
        customerOrder.setStatus(Status.OPEN);

        Order order = this.orderRepository.save(customerOrder);
        
        return new OrderResponse(order);
    }
}
