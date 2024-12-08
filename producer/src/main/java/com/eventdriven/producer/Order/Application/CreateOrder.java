package com.eventdriven.producer.Order.Application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eventdriven.producer.Order.Application.Service.CreateOrderRequest;
import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Order.Domain.ValueObject.Status;

@Component
public class CreateOrder {

    @Autowired
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
