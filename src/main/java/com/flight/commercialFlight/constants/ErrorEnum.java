package com.flight.commercialFlight.constants;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    F101("Record not found");
    private String message;

    ErrorEnum(String message) {
        this.message = message;
    }
}
