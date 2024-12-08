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
public class DeleteLineFromOrderTest {
    @Mock 
    private OrderRepository orderRepository;

    @InjectMocks
    private DeleteOrderLine useCase;

    @Test
    void testRemoveLineFromOpenOrder() {
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("item 1", 1, 1.60));
        items.add(new LineItem("item 2", 1, 2.60));

        Order order = new Order(new Address("", ""), items);
        order.setStatus(Status.OPEN);
        order.setId(102L);
        
        assertEquals(2, order.getItems().size());
        
        when(orderRepository.findById(102L)).thenReturn(order);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            order.getItems().remove(0);
            return null;
        }).when(orderRepository).deleteLine(Mockito.any(), Mockito.any());

        OrderResponse response = useCase.invoke(102L, 0);

        assertEquals(1, response.getListItems().size());
    }

    @Test
    void testRemoveLineFromClosedOrder() {
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem("item 1", 1, 1.60));
        items.add(new LineItem("item 2", 1, 2.60));

        Order order = new Order(new Address("", ""), items);
        order.setStatus(Status.CLOSED);
        order.setId(102L);
        
        assertEquals(2, order.getItems().size());
        
        when(orderRepository.findById(102L)).thenReturn(order);

        assertThrows(IllegalStateException.class, () -> {
            useCase.invoke(102L, 0);
        });
    }
}
