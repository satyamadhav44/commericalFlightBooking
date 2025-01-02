package com.flight.commercialFlight.controller;

import com.flight.commercialFlight.constants.CustomHeaders;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.service.FlightManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flight.commercialFlight.constants.Paths.ADD_NEW_PLANE;
import static com.flight.commercialFlight.constants.Paths.ROOT;


@RestController
@RequestMapping(ROOT)
@Slf4j
public class FlightBookingOperation {

    @Autowired
    private FlightManagementService flightManagementService;

    @PostMapping(ADD_NEW_PLANE)
    public ResponseEntity<BaseResponse> addNewPlane(@RequestHeader CustomHeaders headers, @RequestBody FlightDetails flightDetails) {
      log.info("the header id parameter is : {} ",headers.getxCorrelationId());
        return ResponseEntity.ok(flightManagementService.onboardNewFlights(flightDetails));
    }

}
