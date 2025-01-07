package com.eventdriven.producer.Order.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.eventdriven.producer.order.application.UpdateOrderAddress;
import com.eventdriven.producer.order.application.service.OrderResponse;
import com.eventdriven.producer.order.domain.Order;
import com.eventdriven.producer.order.domain.OrderRepository;
import com.eventdriven.producer.order.domain.vo.Address;
import com.eventdriven.producer.order.domain.vo.Status;

@ExtendWith(MockitoExtension.class)
public class UpdateOrderAddressTest {
    @Mock 
    private OrderRepository orderRepository;

    @InjectMocks
    private UpdateOrderAddress useCase;

    @Test
    void testChangeAddressIntoOpenOrder() {
        Order order = new Order(new Address("", ""), new ArrayList<>());
        order.setStatus(Status.OPEN);
        order.setId(103L);
        
        when(orderRepository.findById(103L)).thenReturn(order);

        Address newAddress = new Address("Test Steet 41", "Floor 3th");
        when(orderRepository.save(order)).thenReturn(order);

        OrderResponse response = useCase.invoke(103L, newAddress);

        assertEquals("Test Steet 41 - Floor 3th", response.getAddress());
    }

    @Test
    void testAddLineIntoClosedOrder() {
        Order order = new Order(new Address("", ""), new ArrayList<>());
        order.setStatus(Status.CLOSED);
        order.setId(103L);
        
        when(orderRepository.findById(103L)).thenReturn(order);

        Address newAddress = new Address("Test Steet 41", "Floor 3th");

        assertThrows(IllegalStateException.class, () -> {
            useCase.invoke(103L, newAddress);
        });
    }
}
