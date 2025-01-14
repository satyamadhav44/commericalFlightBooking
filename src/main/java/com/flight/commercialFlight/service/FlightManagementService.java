package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.mongodb.MongoClientException;
import reactor.core.publisher.Mono;

public interface FlightManagementService {

    Mono<BaseResponse> onboardNewFlights(FlightDetails flightDetails) throws MongoClientException;

    Mono<BaseResponse> updateFlightInformation(FlightDetails flightDetails, String flightId);

    Mono<BaseResponse> fetchAllFlightInformation();

    Mono<BaseResponse> fetchFlightbyId(String flightId);

    Mono<BaseResponse> searchFlights(String departure, String destination);

    Mono<BaseResponse> filterFlightsByDateTime(String datetime);
}
