package com.eventdriven.producer.order.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventdriven.producer.order.application.service.OrderResponse;
import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.LineItem;
import com.eventdriven.producer.order.domain.vo.Status;

@ExtendWith(MockitoExtension.class)
public class AddOrderLinesTest {
    @Mock 
    private OrderRepository orderRepository;

    @InjectMocks
    private AddOrderLines useCase;

    @Test
    void testAddLineIntoOpenOrder() {
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("item 2", 1, 1.60));

        Order order = new Order(new Address("", ""), items);
        order.setStatus(Status.OPEN);
        order.setId(101L);
        
        assertEquals(1, order.getItems().size());
        
        when(orderRepository.findById(101L)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

        LineItem item = new LineItem("item 2", 1, 1.60);
        List<LineItem> newItems = List.of(item);

        OrderResponse response = useCase.invoke(101L, newItems);

        assertEquals(2, response.getListItems().size());
    }

    @Test
    void testAddLineIntoClosedOrder() {
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("item 2", 1, 1.60));

        Order order = new Order(new Address("", ""), items);
        order.setStatus(Status.CLOSED);
        order.setId(101L);
        
        assertEquals(1, order.getItems().size());
        
        when(orderRepository.findById(101L)).thenReturn(order);

        LineItem item = new LineItem("item 2", 1, 1.60);
        List<LineItem> newItems = List.of(item);

        assertThrows(IllegalStateException.class, () -> {
            useCase.invoke(101L, newItems);
        });
    }
}
