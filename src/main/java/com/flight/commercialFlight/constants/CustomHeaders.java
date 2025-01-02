package com.flight.commercialFlight.constants;

import org.springframework.http.HttpHeaders;

public class CustomHeaders extends HttpHeaders {

    public static final String X_CORRELATION_ID="x-correlation-id";
    public static final String X_ACC_OP="x-acc-op";
    public static final String X_ACC_JWT="x-acc-jwt";

    public CustomHeaders(){
        super();
    }

    public void setCorrelationId(String id){
        this.set(X_CORRELATION_ID,id);
    }

    public void setxAccOp(String accOp){
        this.set(X_ACC_OP,accOp);
    }
    public void setxAccJwt(String accJwt){
        this.set(X_ACC_JWT,accJwt);
    }
    public String getxCorrelationId(){
        return this.getFirst(X_CORRELATION_ID);
    }
    public String getxAccOp(){
        return this.getFirst(X_ACC_OP);
    }
    public String getxAccJwt(){
        return this.getFirst(X_ACC_JWT);
    }
}
