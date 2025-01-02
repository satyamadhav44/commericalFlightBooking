package com.flight.commercialFlight.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NonNull
public class FlightDetails {

    private String number;
    private String airline;
    private LocalDate depTime;
    private LocalDate arrTime;
    private String depLocation;
    private String destination;
}
