package com.eventdriven.producer.Order.Application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

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
import com.eventdriven.producer.Order.Domain.ValueObject.Status;

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

        Mockito.doAnswer((Answer<Void>) invocation -> {
            order.setAddress(newAddress);
            return null;
        }).when(orderRepository).editAddress(Mockito.any(), Mockito.any());

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
