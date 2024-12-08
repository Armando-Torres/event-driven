package com.eventdriven.producer.Order.Domain.ValueObject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineItem {
    private String desc;

    private Integer quantity;

    private Double price;

    @Schema(hidden = true)
    public Double getAmount() {
        return this.quantity * this.price;
    }
}
