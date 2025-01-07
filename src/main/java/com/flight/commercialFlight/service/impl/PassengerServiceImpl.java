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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.flight.commercialFlight.constants.MessagesAndCodes.*;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Autowired
    private PassengerRepo passengerRepo;

    @Override
    public BaseResponse getAllPassengers() {
        List<PassengerDetails> passengerDetails = new ArrayList<>();
        passengerRepo.findAll().forEach(each -> {
            passengerDetails.add(Converter.entityToDto(each));
        });
        return BaseResponse.builder().data(passengerDetails).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse registerPassenger(PassengerDetails passengerDetails) {
        Passenger passenger = Converter.dtoToEntity(passengerDetails);
        passengerRepo.insert(passenger);
        return BaseResponse.builder().data(RECORD_ADDED).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse updatePassengerDetails(PassengerDetails passengerDetails) {
        if (passengerRepo.existsById(passengerDetails.getId())) {
            Passenger passenger = Converter.dtoToEntity(passengerDetails);
            passengerRepo.save(passenger);
        } else {
            throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
        }
        return BaseResponse.builder().data(RECORD_UPDATED).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse getPassengerInfo(String passengerId) {
        BaseResponse baseResponse = new BaseResponse();
        Optional.of(passengerRepo.existsById(passengerId)).ifPresentOrElse(
                val -> {
                    baseResponse.setStatusCode(HTTP_200);
                    baseResponse.setData(Converter.entityToDto(passengerRepo.findById(passengerId).get()));
                },
                () -> {
                    baseResponse.setData(ErrorEnum.F101.getMessage());
                    baseResponse.setStatusCode(ErrorEnum.F101.name());
                }
        );
        return baseResponse;
    }
}
