package com.eventdriven.producer.Order.Application;

import java.util.List;
import java.util.Random;

import com.eventdriven.producer.Order.Application.Service.OrderRequest;
import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Address;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Shared.Domain.LineItem;

public class CreateOrder {

    public OrderResponse invoke(OrderRequest orderData) {
        Address customerAddress = new Address(orderData.getFullAddress(), orderData.getAddressDetail());
        List<LineItem> customerOrderLines = orderData.getOrderLines();
        
        Order customerOrder = new Order(customerAddress, customerOrderLines);

        // NOTICE: Setted for demo purposes only
        customerOrder.setVat(21.10);
        customerOrder.setId(new Random().nextLong(100L));
        
        return new OrderResponse(customerOrder);
    }
}
