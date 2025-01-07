package com.eventdriven.producer.order.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.OrderCriteria;

@Repository
public class OrderInMemoryRepository implements OrderRepository{
    HashMap<Long, Order> repository = new HashMap<Long, Order>(1000);

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            Long id =  Integer.valueOf(this.repository.size() + 100).longValue();
            order.setId(id);
        }

        this.repository.put(order.getId(), order);

        return order;
    }

    @Override
    public Order findById(Long orderId) {
        return Optional.ofNullable(this.repository.get(orderId)).orElseThrow(() -> new NoSuchElementException("No order found with id"));
    }

    @Override
    public List<Order> findAll(OrderCriteria criteria) {
        return (criteria.getAddress().getAddress() != null)
            ? this.repository.values().stream().filter(order -> order.getAddress().getAddress().equalsIgnoreCase(criteria.getAddress().getAddress()) ).toList() 
            : this.repository.values().stream().toList();
    }
}
