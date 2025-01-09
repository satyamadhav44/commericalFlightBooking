package com.flight.commercialFlight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Booking")
public class Booking {
    @Id
    private String bookingId = UUID.randomUUID().toString().split("-")[0];
    private Flight flight;
    private Passenger passenger;
    private Date bookingDate;
}
