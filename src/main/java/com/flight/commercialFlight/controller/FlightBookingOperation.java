package com.flight.commercialFlight.controller;

import com.flight.commercialFlight.constants.CustomHeaders;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.dto.PassengerDetails;
import com.flight.commercialFlight.service.BookingService;
import com.flight.commercialFlight.service.FlightManagementService;
import com.flight.commercialFlight.service.PassengerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.flight.commercialFlight.constants.Paths.*;

@RestController
@RequestMapping(ROOT)
@Slf4j
public class FlightBookingOperation {

    @Autowired
    private FlightManagementService flightManagementService;

    @Autowired
    private PassengerService passengerService;

    @Autowired
    private BookingService bookingService;

    @PostMapping(ADD_NEW_PLANE)
    public ResponseEntity<BaseResponse> addnewplane(@RequestHeader HttpHeaders headers, @RequestBody @Validated FlightDetails flightDetails) {
        log.info("the header id parameter is : {} ", headers.get(CustomHeaders.X_CORRELATION_ID));
        return ResponseEntity.ok(flightManagementService.onboardNewFlights(flightDetails));
    }

    @PatchMapping(UPDATE_PLANE_INFO)
    public ResponseEntity<BaseResponse> updateflightinfo(@RequestHeader HttpHeaders headers, @RequestBody FlightDetails flightDetails, @PathVariable("flight_id") String flightId) {
        return ResponseEntity.ok(flightManagementService.updateFlightInformation(flightDetails, flightId));
    }

    @GetMapping(FETCH_FLIGHT_ALL)
    public ResponseEntity<BaseResponse> getallflights(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(flightManagementService.fetchAllFlightInformation());
    }

    @GetMapping(FETCH_FLIGHT_DETAIL)
    public ResponseEntity<BaseResponse> getflightdetail(@RequestHeader HttpHeaders headers, @PathVariable("flight_id") String flightId) {
        return ResponseEntity.ok(flightManagementService.fetchFlightbyId(flightId));
    }

    @GetMapping(FETCH_PASSENGER_ALL)
    public ResponseEntity<BaseResponse> getallpassengerinfo(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @PostMapping(REGISTER_PASSENGER)
    public ResponseEntity<BaseResponse> registerpassenger(@RequestHeader HttpHeaders headers, @RequestBody PassengerDetails passengerDetails) {
        return ResponseEntity.ok(passengerService.registerPassenger(passengerDetails));
    }

    @PatchMapping(UPDATE_PASSENGER)
    public ResponseEntity<BaseResponse> updatepassenger(@RequestHeader HttpHeaders headers, @RequestBody PassengerDetails passengerDetails) {
        return ResponseEntity.ok(passengerService.updatePassengerDetails(passengerDetails));
    }

    @GetMapping(FETCH_PASENG_ID)
    public ResponseEntity<BaseResponse> getpassengerdetails(@RequestHeader HttpHeaders headers, @PathVariable("passenger_id") String passengerId) {
        return ResponseEntity.ok(passengerService.getPassengerInfo(passengerId));
    }

    @PostMapping(CREATE_BOOKING)
    public ResponseEntity<BaseResponse> createbooking(@RequestHeader HttpHeaders headers, @PathVariable("flight_id") String flightId, @PathVariable("passenger_id") String passengerId) throws InterruptedException {
        return ResponseEntity.ok(bookingService.makeFlightBooking(flightId, passengerId));
    }

    @GetMapping(ALL_BOOKING)
    public ResponseEntity<BaseResponse> getallbookingdetails(@RequestHeader HttpHeaders headers) {
        return ResponseEntity.ok(bookingService.getAlltheBookings());
    }

    @GetMapping(BOOKING_BY_FLIGHT_NUM)
    public ResponseEntity<BaseResponse> getbookingbyflight(@RequestHeader HttpHeaders headers, @PathVariable("flight_id") String flightId) {
        return ResponseEntity.ok(bookingService.viewBookingByFlight(flightId));
    }

    // Hard delete
    @DeleteMapping(CANCLE_BOOKING)
    public ResponseEntity<BaseResponse> canclebooking(@RequestHeader HttpHeaders headers, @PathVariable("booking_id") String bookingId) {
        return ResponseEntity.ok(bookingService.cancelFlightBooking(bookingId));
    }

    @GetMapping(BOOKING_BY_PASSENGER_NUM)
    public ResponseEntity<BaseResponse> getbookingbypassenger(@RequestHeader HttpHeaders headers, @PathVariable("passenger_id") String passengerId) {
        return ResponseEntity.ok(bookingService.viewBookingByPassenger(passengerId));
    }

    @GetMapping(SEARCH_FLIGHT)
    public ResponseEntity<BaseResponse> getflightbydeptureanddest(@RequestHeader HttpHeaders headers, @PathVariable("departure") String dept, @PathVariable("destination")
    String dest) {
        return ResponseEntity.ok(flightManagementService.searchFlights(dept, dest));
    }


}
