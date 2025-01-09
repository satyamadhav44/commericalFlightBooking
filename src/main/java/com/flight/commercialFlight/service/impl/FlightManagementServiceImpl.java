package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.entity.Flight;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.FlightRepo;
import com.flight.commercialFlight.service.FlightManagementService;
import com.flight.commercialFlight.utils.Converter;
import com.mongodb.MongoClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.flight.commercialFlight.constants.MessagesAndCodes.HTTP_200;
import static com.flight.commercialFlight.constants.MessagesAndCodes.RECORD_ADDED;

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
        return BaseResponse.builder().statusCode(HTTP_200).data(RECORD_ADDED).build();
    }

    /**
     * @param flightDetails
     * @return
     */
    @Override
    public BaseResponse updateFlightInformation(FlightDetails flightDetails, String flightId) {
        flightDetails.setNumber(flightId);
        Flight flight = Converter.dtoToEntity(flightDetails);
        if (flightRepo.existsById(flight.getNumber())) {
            flightRepo.save(flight);
        } else {
            throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
        }
        return BaseResponse.builder().data("data Updated").statusCode(HTTP_200).build();
    }

    /**
     * @return
     */
    @Override
    public BaseResponse fetchAllFlightInformation() {
        List<FlightDetails> flightDetails = new ArrayList<>();
        flightRepo.findAll().forEach(each -> {
            flightDetails.add(Converter.entityToDto(each));
        });
        return BaseResponse.builder().data(flightDetails).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse fetchFlightbyId(String flightId) {
        FlightDetails flightDetails = new FlightDetails();
        BaseResponse baseResponse = new BaseResponse();
        Optional<Flight> flightOptional = flightRepo.findById(flightId);
        if (flightOptional.isPresent()) {
            flightDetails = Converter.entityToDto(flightOptional.get());
            baseResponse.setData(flightDetails);
            baseResponse.setStatusCode(HTTP_200);
        } else {
            baseResponse.setData(ErrorEnum.F101.getMessage());
            baseResponse.setStatusCode(ErrorEnum.F101.name());
        }
        return baseResponse;
    }

    @Override
    public BaseResponse searchFlights(String departure, String destination) {
        List<FlightDetails> flightDetails = new ArrayList<>();
        flightRepo.findAll().stream().filter(e -> e.getDepLocation().equals(departure) && e.getDestination().equals(destination)).toList().forEach(
                each -> {
                    flightDetails.add(Converter.entityToDto(each));
                }
        );
        return BaseResponse.builder().data(flightDetails).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse filterFlightsByDateTime(String datetime) {
        return null;
    }
}
