package com.flight.commercialFlight.repository;

import com.flight.commercialFlight.entity.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepo extends MongoRepository<Flight, String> {
}
