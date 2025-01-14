package com.flight.commercialFlight.repository;


import com.flight.commercialFlight.entity.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookingRepo extends ReactiveMongoRepository<Booking, String> {
    Flux<Booking> findAllByFlightNumber(String flightNo);

    Flux<Booking> findAllByPassengerId(String passId);
}
