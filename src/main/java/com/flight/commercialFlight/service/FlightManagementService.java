package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.mongodb.MongoClientException;

public interface FlightManagementService {

    public BaseResponse onboardNewFlights(FlightDetails flightDetails) throws MongoClientException;
    public BaseResponse updateFlightInformation(FlightDetails flightDetails,String flightId);
    public BaseResponse fetchAllFlightInformation();
    public BaseResponse fetchFlightbyId(String flightId);
}
