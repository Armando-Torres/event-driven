package com.eventdriven.producer.Shared.Application.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer code;

    private String message;
}
