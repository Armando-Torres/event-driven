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
class DeleteLineFromOrderTest {
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
        when(orderRepository.save(order)).thenReturn(order);

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
