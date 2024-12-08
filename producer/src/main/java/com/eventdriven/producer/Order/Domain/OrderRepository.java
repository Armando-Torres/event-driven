package com.eventdriven.producer.Order.Domain;

import java.util.List;

import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Order.Domain.ValueObject.OrderCriteria;

public interface OrderRepository {
    public Order save(Order order);

    public Order findById(Long orderId);

    public List<Order> findAll(OrderCriteria criteria);

    public void addLine(Long orderId, LineItem line);

    public void deleteLine(Long orderId, Integer lineRowNumber);

    public void editAddress(Long orderId, Address address);
}
