package com.eventdriven.producer.Shared.Domain;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderCalculator {
    
    public Double calculateNet(List<LineItem> orderLines) {
        return 0.0;
    }
}
