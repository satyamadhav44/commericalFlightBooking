package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;
import reactor.core.publisher.Mono;

public interface BookingService {

    Mono<BaseResponse> makeFlightBooking(String flightNo, String passengerId) throws InterruptedException;

    Mono<BaseResponse> cancelFlightBooking(String bookingId);

    Mono<BaseResponse> viewBookingByFlight(String flightNum);

    Mono<BaseResponse> viewBookingByPassenger(String passId);

    Mono<BaseResponse> getAlltheBookings();
}
