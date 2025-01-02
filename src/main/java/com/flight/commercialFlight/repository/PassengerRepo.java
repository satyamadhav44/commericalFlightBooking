package com.flight.commercialFlight.repository;


import com.flight.commercialFlight.entity.Passenger;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepo extends MongoRepository<Passenger, String> {
}
