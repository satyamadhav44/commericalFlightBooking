package com.flight.commercialFlight.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NonNull
public class FlightDetails {
    
    private String number;
    private String airline;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String depTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")// YYYY-MM-DDTHH:mm:ss
    private String arrTime; // YYYY-MM-DDTHH:mm:ss
    private String depLocation;
    private String destination;
}
