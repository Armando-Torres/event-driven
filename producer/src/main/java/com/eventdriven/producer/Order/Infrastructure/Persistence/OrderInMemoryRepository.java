package com.eventdriven.producer.Order.Infrastructure.Persistence;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Order.Domain.ValueObject.OrderCriteria;

@Repository
public class OrderInMemoryRepository implements OrderRepository{
    HashMap<Long, Order> repository = new HashMap<Long, Order>(1000);

    @Override
    public Order save(Order order) {
        Long lastId =  Integer.valueOf(this.repository.size() + 100).longValue();

        order.setId(lastId);
        this.repository.put(lastId, order);

        return order;
    }

    @Override
    public Order findById(Long orderId) {
        return Optional.ofNullable(this.repository.get(orderId)).orElseThrow(() -> new NoSuchElementException("No order found with id"));
    }

    @Override
    public List<Order> findAll(OrderCriteria criteria) {
        return this.repository.values().stream().toList();
    }

    @Override
    public void addLine(Long orderId, LineItem line) {
        Order order = Optional.ofNullable(this.repository.get(orderId)).orElseThrow(() -> new NoSuchElementException("No order found with id"));

        List<LineItem> lines = order.getItems();
        lines.add(line);

        order.setItems(lines);

        this.repository.replace(order.getId(), order);
    }

    @Override
    public void deleteLine(Long orderId, Integer lineRowNumber) {
        Order order = Optional.ofNullable(this.repository.get(orderId)).orElseThrow(() -> new NoSuchElementException("No order found with id"));

        List<LineItem> lines = order.getItems();
        lines.remove(lineRowNumber.intValue());

        order.setItems(lines);

        this.repository.replace(order.getId(), order);
    }

    @Override
    public void editAddress(Long orderId, Address address) {
        Order order = Optional.ofNullable(this.repository.get(orderId)).orElseThrow(() -> new NoSuchElementException("No order found with id"));

        order.setAddress(address);

        this.repository.replace(order.getId(), order);
    }
    
}
