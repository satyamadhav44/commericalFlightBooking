package com.flight.commercialFlight.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NonNull
public class PassengerDetails {
    private String name;
    private String passportNum;
    private String id;
}
