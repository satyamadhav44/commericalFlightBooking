package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.mongodb.MongoClientException;

public interface FlightManagementService {

    BaseResponse onboardNewFlights(FlightDetails flightDetails) throws MongoClientException;

    BaseResponse updateFlightInformation(FlightDetails flightDetails, String flightId);

    BaseResponse fetchAllFlightInformation();

    BaseResponse fetchFlightbyId(String flightId);

    BaseResponse searchFlights(String departure, String destination);

    BaseResponse filterFlightsByDateTime(String datetime);
}
