package com.flight.commercialFlight.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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

