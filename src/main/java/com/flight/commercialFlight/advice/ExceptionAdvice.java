package com.flight.commercialFlight.advice;

import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.exception.CustomException;
import com.mongodb.MongoClientException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({CustomException.class, MongoClientException.class})
    public ResponseEntity<BaseResponse> customerExceptionHandler(CustomException ex){
        BaseResponse baseResponse = new BaseResponse(ex.getMessage(),ex.getErrorCode());
        return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
