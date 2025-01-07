package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.PassengerDetails;

public interface PassengerService {

    BaseResponse getAllPassengers();

    BaseResponse registerPassenger(PassengerDetails passengerDetails);

    BaseResponse updatePassengerDetails(PassengerDetails passengerDetails);

    BaseResponse getPassengerInfo(String passengerId);
}
