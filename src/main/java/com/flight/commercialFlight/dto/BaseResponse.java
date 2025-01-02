package com.flight.commercialFlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse {
    private Object data;
    private String statusCode;
    private String error;

    public BaseResponse(String message, String statusCode) {
        this.error = message;
        this.statusCode = statusCode;
    }

    public BaseResponse(Object object,String statusCode){
        this.statusCode=statusCode;
        this.data=object;
    }
}

