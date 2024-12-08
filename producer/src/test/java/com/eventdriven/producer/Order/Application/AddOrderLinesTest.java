package com.eventdriven.producer.Order.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.eventdriven.producer.Order.Application.Service.OrderResponse;
import com.eventdriven.producer.Order.Domain.Order;
import com.eventdriven.producer.Order.Domain.OrderRepository;
import com.eventdriven.producer.Order.Domain.ValueObject.Address;
import com.eventdriven.producer.Order.Domain.ValueObject.LineItem;
import com.eventdriven.producer.Order.Domain.ValueObject.Status;

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

        LineItem item = new LineItem("item 2", 1, 1.60);
        List<LineItem> newItems = List.of(item);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            order.getItems().add(item);
            return null;
        }).when(orderRepository).addLine(Mockito.any(), Mockito.any());

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
