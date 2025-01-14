package com.flight.commercialFlight.constants;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    F101("Record not found"),
    F102("SQL Error");
    private final String message;

    ErrorEnum(String message) {
        this.message = message;
    }
}
