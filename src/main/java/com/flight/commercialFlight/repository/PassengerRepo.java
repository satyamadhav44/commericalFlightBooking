package com.flight.commercialFlight.repository;


import com.flight.commercialFlight.entity.Passenger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PassengerRepo extends ReactiveMongoRepository<Passenger, String> {
}
