package com.flight.commercialFlight.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.entity.Flight;

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
}

