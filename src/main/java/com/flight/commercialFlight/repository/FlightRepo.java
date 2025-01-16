package com.flight.commercialFlight.repository;

import com.flight.commercialFlight.entity.Flight;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FlightRepo extends ReactiveMongoRepository<Flight, String> {

    Flux<Flight> findByDepLocationAndDestination(String dep, String dest);
}
