package com.flight.commercialFlight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Flight")
public class Flight {
    @Id
    private String number;
    private String airline;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String depTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String arrTime;
    private String depLocation;
    private String destination;
}
