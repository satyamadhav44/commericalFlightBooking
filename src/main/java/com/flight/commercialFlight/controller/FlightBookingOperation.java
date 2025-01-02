package com.flight.commercialFlight.controller;

import com.flight.commercialFlight.constants.CustomHeaders;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.service.FlightManagementService;
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

    @PostMapping(ADD_NEW_PLANE)
    public ResponseEntity<BaseResponse> addNewPlane(@RequestHeader HttpHeaders headers, @RequestBody @Validated FlightDetails flightDetails) {
      log.info("the header id parameter is : {} ",headers.get(CustomHeaders.X_CORRELATION_ID));
        return ResponseEntity.ok(flightManagementService.onboardNewFlights(flightDetails));
    }

    @PutMapping(UPDATE_PLANE_INFO)
    public ResponseEntity<BaseResponse> updateFlightInfo(@RequestHeader HttpHeaders headers, @RequestBody FlightDetails flightDetails,@PathVariable("flight_id") String flightId){
        return ResponseEntity.ok(flightManagementService.updateFlightInformation(flightDetails,flightId));
    }

    @GetMapping(FETCH_FLIGHT_ALL)
    public ResponseEntity<BaseResponse> getAllFlights(@RequestHeader HttpHeaders headers){
        return ResponseEntity.ok(flightManagementService.fetchAllFlightInformation());
    }

    @GetMapping(FETCH_FLIGHT_DETAIL)
    public ResponseEntity<BaseResponse> getFlightDetail(@RequestHeader HttpHeaders headers, @PathVariable("flight_id") String flightId){
        return ResponseEntity.ok(flightManagementService.fetchFlightbyId(flightId));
    }

}
