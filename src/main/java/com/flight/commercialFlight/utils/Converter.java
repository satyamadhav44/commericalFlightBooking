package com.flight.commercialFlight.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.commercialFlight.dto.BookingDetails;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.dto.PassengerDetails;
import com.flight.commercialFlight.entity.Booking;
import com.flight.commercialFlight.entity.Flight;
import com.flight.commercialFlight.entity.Passenger;

public class Converter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Convert DTO to Entity
    public static Flight dtoToEntity(FlightDetails flightDTO) {
        return objectMapper.convertValue(flightDTO, Flight.class);
    }

    // Convert Entity to DTO
    public static FlightDetails entityToDto(Flight flightEntity) {
        return objectMapper.convertValue(flightEntity, FlightDetails.class);
    }

    public static Passenger dtoToEntity(PassengerDetails passengerDetails) {
        return objectMapper.convertValue(passengerDetails, Passenger.class);
    }

    // Convert Entity to DTO
    public static PassengerDetails entityToDto(Passenger passengerEntity) {
        return objectMapper.convertValue(passengerEntity, PassengerDetails.class);
    }

    public static Booking dtoToEntity(BookingDetails bookingDetails) {
        return objectMapper.convertValue(bookingDetails, Booking.class);
    }

    // Convert Entity to DTO
    public static BookingDetails entityToDto(Booking bookingEntity) {
        return objectMapper.convertValue(bookingEntity, BookingDetails.class);
    }
}

