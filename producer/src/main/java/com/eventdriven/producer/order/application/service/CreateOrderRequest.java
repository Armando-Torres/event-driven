package com.eventdriven.producer.order.application.service;

import java.util.List;

import com.eventdriven.producer.order.domain.vo.LineItem;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderRequest {
    private String fullAddress;

    private String addressDetail;

    /* NOTICE: 
     *   Setted price in the request for demo purposes only
     *      It will be get from your persistence to avoid inconsistant modification 
     *      or external manipulation of value
     */
    private List<LineItem> orderLines;
}
