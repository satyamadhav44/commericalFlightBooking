package com.flight.commercialFlight.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Passenger")
public class Passenger {
    @Id
    private String id;
    private String name;
    private String passportNum;
}
