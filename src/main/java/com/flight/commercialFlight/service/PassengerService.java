package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.PassengerDetails;
import reactor.core.publisher.Mono;

public interface PassengerService {

    Mono<BaseResponse> getAllPassengers();

    Mono<BaseResponse> registerPassenger(PassengerDetails passengerDetails);

    Mono<BaseResponse> updatePassengerDetails(PassengerDetails passengerDetails);

    Mono<BaseResponse> getPassengerInfo(String passengerId);
}
