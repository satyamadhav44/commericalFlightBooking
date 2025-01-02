package com.flight.commercialFlight.repository;


import com.flight.commercialFlight.entity.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepo extends MongoRepository<Booking, String> {
}
