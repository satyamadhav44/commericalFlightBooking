package com.flight.commercialFlight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Flight")
public class Flight {
    @Id
    private String number;
    private String airline;
    private LocalDate depTime;
    private LocalDate arrTime;
    private String depLocation;
    private String destination;
}
