package com.flight.commercialFlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetails {
    private String bookingId;
    private FlightDetails flight;
    private PassengerDetails passenger;
    private LocalDate bookingDate;
}
