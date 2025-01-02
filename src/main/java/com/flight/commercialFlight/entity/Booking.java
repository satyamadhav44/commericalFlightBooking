package com.flight.commercialFlight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Booking")
public class Booking {
    @Id
    private String bookingId;
    private Flight flight;
    private Passenger passengerId;
    private LocalDate bookingDate;
}
