package com.flight.commercialFlight.dto;

import com.flight.booking.system.commercialflight.entity.Flight;
import com.flight.booking.system.commercialflight.entity.Passenger;
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
    private Flight flight;
    private Passenger passenger;
    private LocalDate bookingDate;
}
