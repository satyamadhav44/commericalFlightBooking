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
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.flight.commercialFlight.constants.ErrorEnum.F102;
import static com.flight.commercialFlight.constants.MessagesAndCodes.*;

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
    public Mono<BaseResponse> onboardNewFlights(FlightDetails flightDetails) throws MongoClientException {
        Flight flightEntity = Converter.dtoToEntity(flightDetails);
        return flightRepo.insert(flightEntity)
                .doOnError(err -> BaseResponse.builder().data(err.getMessage()).statusCode(F102.name()).build())
                .map(value -> BaseResponse.builder().data(RECORD_ADDED).statusCode(HTTP_200).build());
    }

    /**
     * @param flightDetails
     * @return
     */
    @Override
    public Mono<BaseResponse> updateFlightInformation(FlightDetails flightDetails, String flightId) {
        flightDetails.setNumber(flightId);
        Flight flight = Converter.dtoToEntity(flightDetails);
        return flightRepo.existsById(flightId)
                .flatMap(
                        exists -> {
                            if (exists) {
                                return flightRepo.save(flight).map(value -> BaseResponse.builder().data(RECORD_UPDATED).statusCode(HTTP_200).build());
                            } else {
                                return Mono.just(BaseResponse.builder().data(ErrorEnum.F101.getMessage()).statusCode(ErrorEnum.F101.name()).build());
                            }
                        });
    }

    /**
     * @return
     */
    @Override
    public Mono<BaseResponse> fetchAllFlightInformation() {
        List<FlightDetails> flightDetails = new ArrayList<>();
        return flightRepo.findAll().collectList()
                .map(value -> {
                    value.forEach(each -> {
                        FlightDetails details = Converter.entityToDto(each);
                        flightDetails.add(details);
                    });
                    return BaseResponse.builder().data(flightDetails).statusCode(HTTP_200).build();
                });
    }

    @Override
    public Mono<BaseResponse> fetchFlightbyId(String flightId) {
        FlightDetails flightDetails = new FlightDetails();
        return flightRepo.existsById(flightId)
                .doOnError(err -> {
                    throw new RuntimeException(err.getMessage());
                })
                .flatMap(exists -> {
                    if (exists) {
                        return flightRepo.findById(flightId)
                                .map(flight -> BaseResponse.builder().data(Converter.entityToDto(flight)).statusCode(HTTP_200).build());
                    } else {
                        return Mono.just(BaseResponse.builder().data(ErrorEnum.F101.getMessage()).statusCode(ErrorEnum.F101.name()).build());

                    }
                });
    }

    @Override
    public Mono<BaseResponse> searchFlights(String departure, String destination) {
        List<FlightDetails> flightDetails = new ArrayList<>();
        return flightRepo.findAll().collectSortedList()
                .flatMap(listValues -> {
                    listValues.stream().filter(e -> e.getDepLocation().equalsIgnoreCase(departure) && e.getDestination().equalsIgnoreCase(destination))
                            .forEach(each -> {
                                flightDetails.add(Converter.entityToDto(each));
                            });
                    return Mono.just(BaseResponse.builder().data(flightDetails).statusCode(HTTP_200).build());
                }).doOnError(err -> {
                    throw new CustomException(err.getMessage(), HTTP_500);
                });
    }

    @Override
    public Mono<BaseResponse> filterFlightsByDateTime(String datetime) {
        return Mono.empty();
    }
}
