package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.PassengerDetails;
import com.flight.commercialFlight.entity.Passenger;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.PassengerRepo;
import com.flight.commercialFlight.service.PassengerService;
import com.flight.commercialFlight.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.flight.commercialFlight.constants.MessagesAndCodes.*;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepo passengerRepo;

    @Override
    public Mono<BaseResponse> getAllPassengers() {
        List<PassengerDetails> passengerDetails = new ArrayList<>();
        return passengerRepo.findAll().collectSortedList().map(
                listValues -> {
                    listValues.forEach(each -> {
                        passengerDetails.add(Converter.entityToDto(each));
                    });
                    return BaseResponse.builder().data(passengerDetails).statusCode(HTTP_200).build();
                }
        );
    }

    @Override
    public Mono<BaseResponse> registerPassenger(PassengerDetails passengerDetails) {
        Passenger passenger = Converter.dtoToEntity(passengerDetails);
        return passengerRepo.insert(passenger).map(
                        passenger1 -> BaseResponse.builder().data(RECORD_ADDED).statusCode(HTTP_200).build())
                .doOnError(err -> {
                    throw new CustomException(err.getMessage(), ErrorEnum.F102.name());
                });
    }

    @Override
    public Mono<BaseResponse> updatePassengerDetails(PassengerDetails passengerDetails) {
        return passengerRepo.existsById(passengerDetails.getId())
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        Passenger passenger = Converter.dtoToEntity(passengerDetails);
                        return passengerRepo.save(passenger).map(done -> BaseResponse.builder().data(RECORD_UPDATED).statusCode(HTTP_200).build());
                    } else {
                        return Mono.just(BaseResponse.builder().data(ErrorEnum.F101.getMessage()).statusCode(ErrorEnum.F101.name()).build());
                    }
                }).doOnError(err -> {
                    throw new CustomException(err.getMessage(), ErrorEnum.F102.name());
                });
    }

    @Override
    public Mono<BaseResponse> getPassengerInfo(String passengerId) {
        return passengerRepo.existsById(passengerId)
                .flatMap(aBoolean -> {
                    if (aBoolean) {
                        return passengerRepo.findById(passengerId).map(value -> BaseResponse.builder().data(Converter.entityToDto(value)).statusCode(HTTP_200).build());
                    } else {
                        return Mono.just(BaseResponse.builder().data(ErrorEnum.F101.getMessage()).statusCode(ErrorEnum.F101.name()).build());
                    }
                }).doOnError(err -> {
                    throw new CustomException(err.getMessage(), ErrorEnum.F102.name());
                });
    }
}
