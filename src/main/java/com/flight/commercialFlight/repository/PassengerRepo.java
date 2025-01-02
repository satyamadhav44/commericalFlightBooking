package com.flight.commercialFlight.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PassengerRepo extends MongoRepository<Passenger, String> {
}
