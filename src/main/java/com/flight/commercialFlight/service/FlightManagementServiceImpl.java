package com.flight.commercialFlight.service;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.entity.Flight;
import com.flight.commercialFlight.repository.FlightRepo;
import com.flight.commercialFlight.utils.Converter;
import com.mongodb.MongoClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FlightManagementServiceImpl implements FlightManagementService {
    /**
     * @param flightDetails
     * @return
     */

    @Autowired
    private FlightRepo flightRepo;

    @Override
    public BaseResponse onboardNewFlights(FlightDetails flightDetails) throws MongoClientException {
        Flight flightEntity = Converter.dtoToEntity(flightDetails);
        flightRepo.insert(flightEntity);
        return BaseResponse.builder().statusCode("200").data("Record inserted").build();
    }

    /**
     * @param flightDetails
     * @return
     */
    @Override
    public BaseResponse updateFlightInformation(FlightDetails flightDetails) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public BaseResponse fetchAllFlightInformation() {
        return null;
    }
}
