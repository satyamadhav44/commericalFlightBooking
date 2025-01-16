package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.PassengerDetails;
import com.flight.commercialFlight.entity.Passenger;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.PassengerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.flight.commercialFlight.constants.MessagesAndCodes.HTTP_200;
import static com.flight.commercialFlight.constants.MessagesAndCodes.RECORD_UPDATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {

    @InjectMocks
    private PassengerServiceImpl passengerService;
    @Mock
    private PassengerRepo passengerRepo;

    @Test
    void getAllPassengers_Success() {
        when(passengerRepo.findAll()).thenReturn(Flux.just(new Passenger("1", "Satya", "R12345"), new Passenger("2", "Reddy", "R12365")));
        Mono<BaseResponse> responseMono = passengerService.getAllPassengers();
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();

    }

    @Test
    void registerPassenger_Success() {
        PassengerDetails passengerDetails = new PassengerDetails("Satya", "R12345", "1");
        when(passengerRepo.insert(any(Passenger.class))).thenReturn(Mono.just(new Passenger("1", "Satya", "R12345")));
        Mono<BaseResponse> responseMono = passengerService.registerPassenger(passengerDetails);
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();

    }

    @Test
    void registerPassenger_Failure() {
        PassengerDetails passengerDetails = new PassengerDetails("Satya", "R12345", "1");
        when(passengerRepo.insert(any(Passenger.class))).thenReturn(Mono.error(new CustomException(ErrorEnum.F102.getMessage(), ErrorEnum.F102.name())));
        Mono<BaseResponse> responseMono = passengerService.registerPassenger(passengerDetails);
        StepVerifier.create(responseMono)
                .expectErrorMatches(res -> res instanceof CustomException && res.getMessage().equals(ErrorEnum.F102.getMessage())).verify();

    }

    @Test
    void updatePassengerDetails_When_RecordExistsTrueSuccess() {
        PassengerDetails passengerDetails = new PassengerDetails("Satya", "R12345", "1");
        when(passengerRepo.existsById(anyString())).thenReturn(Mono.just(true));
        when(passengerRepo.save(any(Passenger.class))).thenReturn(Mono.just(new Passenger("1", "Satya", "R12345")));
        Mono<BaseResponse> responseMono = passengerService.updatePassengerDetails(passengerDetails);
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getData().equals(RECORD_UPDATED)).verifyComplete();
    }

    @Test
    void updatePassengerDetails_When_recordNotExistFalseSuccess() {
        PassengerDetails passengerDetails = new PassengerDetails("Satya", "R12345", "1");
        when(passengerRepo.existsById(anyString())).thenReturn(Mono.just(false));
        Mono<BaseResponse> responseMono = passengerService.updatePassengerDetails(passengerDetails);
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getData().equals(ErrorEnum.F101.getMessage())).verifyComplete();
    }

    @Test
    void updatePassengerDetails_When_ExceptionThrown() {
        PassengerDetails passengerDetails = new PassengerDetails("Satya", "R12345", "1");
        when(passengerRepo.existsById(anyString())).thenReturn(Mono.error(new CustomException(ErrorEnum.F102.getMessage(), ErrorEnum.F102.name())));
        Mono<BaseResponse> responseMono = passengerService.updatePassengerDetails(passengerDetails);
        StepVerifier.create(responseMono)
                .expectErrorMatches(err -> err instanceof CustomException && err.getMessage().equals(ErrorEnum.F102.getMessage()));
    }

    @Test
    void getPassengerInfo_When_RecordExistsTrueSuccess() {
        when(passengerRepo.existsById(anyString())).thenReturn(Mono.just(true));
        when(passengerRepo.findById(anyString())).thenReturn(Mono.just(new Passenger("1", "Satya", "R12345")));
        Mono<BaseResponse> responseMono = passengerService.getPassengerInfo("1");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();
    }

    @Test
    void getPassengerInfo_When_recordNotExistFalseSuccess() {
        when(passengerRepo.existsById(anyString())).thenReturn(Mono.just(false));
        Mono<BaseResponse> responseMono = passengerService.getPassengerInfo("1");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getData().equals(ErrorEnum.F101.getMessage())).verifyComplete();
    }

    @Test
    void getPassengerInfo_When_ExceptionThrown() {
        when(passengerRepo.existsById(anyString())).thenReturn(Mono.error(new CustomException(ErrorEnum.F102.getMessage(), ErrorEnum.F102.name())));
        Mono<BaseResponse> responseMono = passengerService.getPassengerInfo("1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(err -> err instanceof CustomException && err.getMessage().equals(ErrorEnum.F102.getMessage()));
    }

}
