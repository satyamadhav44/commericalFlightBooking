package com.flight.booking.system.commercialflight.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private String errorCode;
    public CustomException(String message, String code) {
        super(message);
        this.errorCode = code;
    }
}
