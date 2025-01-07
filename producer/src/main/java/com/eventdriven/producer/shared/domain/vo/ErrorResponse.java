package com.eventdriven.producer.shared.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer code;

    private String message;
}
