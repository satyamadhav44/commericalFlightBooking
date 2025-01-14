package com.flight.commercialFlight.repository;

import com.flight.commercialFlight.entity.Flight;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface FlightRepo extends ReactiveMongoRepository<Flight, String> {
}
