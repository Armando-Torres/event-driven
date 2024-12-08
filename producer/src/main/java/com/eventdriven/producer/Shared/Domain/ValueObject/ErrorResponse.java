package com.eventdriven.producer.Shared.Domain.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer code;

    private String message;
}
