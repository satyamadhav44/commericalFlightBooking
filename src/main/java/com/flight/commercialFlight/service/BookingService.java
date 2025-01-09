package com.flight.commercialFlight.service;

import com.flight.commercialFlight.dto.BaseResponse;

public interface BookingService {

    BaseResponse makeFlightBooking(String flightNo, String passengerId) throws InterruptedException;

    BaseResponse cancelFlightBooking(String bookingId);

    BaseResponse viewBookingByFlight(String flightNum);

    BaseResponse viewBookingByPassenger(String passId);
    
    BaseResponse getAlltheBookings();
}
