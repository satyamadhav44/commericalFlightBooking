package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.entity.Flight;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.FlightRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.flight.commercialFlight.constants.ErrorEnum.F102;
import static com.flight.commercialFlight.constants.MessagesAndCodes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FlightManagementServiceImplTest {

    @Mock
    private FlightRepo flightRepo;
    @InjectMocks
    private FlightManagementServiceImpl flightManagementService;

    @Test
    void onboardNewFlights_Success() {
        FlightDetails flightDetails = new FlightDetails();
        when(flightRepo.insert(any(Flight.class))).thenReturn(Mono.just(new Flight()));
        Mono<BaseResponse> responseMono = flightManagementService.onboardNewFlights(flightDetails);
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();
    }

    @Test
    void onboardNewFlights_ThrowsException() {
        FlightDetails flightDetails = new FlightDetails();
        when(flightRepo.insert(any(Flight.class))).thenReturn(Mono.error(new CustomException("error", F102.name())));
        Mono<BaseResponse> responseMono = flightManagementService.onboardNewFlights(flightDetails);
        StepVerifier.create(responseMono)
                .expectErrorMatches(res -> res instanceof CustomException && ((CustomException) res).getErrorCode().equals(F102.name())).verify();
    }

    @Test
    void updateFlightInformation_Success() {
        FlightDetails flightDetails = new FlightDetails();
        when(flightRepo.existsById(anyString())).thenReturn(Mono.just(true));
        when(flightRepo.save(any(Flight.class))).thenReturn(Mono.just(new Flight()));
        Mono<BaseResponse> responseMono = flightManagementService.updateFlightInformation(flightDetails, "1");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getData().equals(RECORD_UPDATED)).verifyComplete();
    }

    @Test
    void updateFlightInformation_Failure() {
        FlightDetails flightDetails = new FlightDetails();
        when(flightRepo.existsById(anyString())).thenReturn(Mono.just(false));
        Mono<BaseResponse> responseMono = flightManagementService.updateFlightInformation(flightDetails, "1");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(ErrorEnum.F101.name())).verifyComplete();
    }

    @Test
    void fetchAllFlightInformation_Success() {
        when(flightRepo.findAll()).thenReturn(Flux.just(new Flight()));
        Mono<BaseResponse> responseMono = flightManagementService.fetchAllFlightInformation();
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();
    }

    @Test
    void fetchFlightbyId_True() {
        when(flightRepo.existsById(anyString())).thenReturn(Mono.just(true));
        Mono<BaseResponse> responseMono = flightManagementService.fetchFlightbyId("1");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200));
    }

    @Test
    void fetchFlightbyId_False() {
        when(flightRepo.existsById(anyString())).thenReturn(Mono.just(false));
        Mono<BaseResponse> responseMono = flightManagementService.fetchFlightbyId("1");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(ErrorEnum.F101.name()));
    }

    @Test
    void fetchFlightbyId_ThrowsException() {
        when(flightRepo.existsById(anyString())).thenReturn(Mono.error(new CustomException("error thrown", F102.name())));
        Mono<BaseResponse> responseMono = flightManagementService.fetchFlightbyId("1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(err -> err instanceof CustomException && ((CustomException) err).getErrorCode().equals(F102.name()));
    }

    @Test
    void searchFlights_Success() {
        when(flightRepo.findByDepLocationAndDestination(anyString(), anyString())).
                thenReturn(Flux.just(new Flight("1", "Air india", "13-01-2025T13:00:00", "13-01-2025T17:40:00", "Singapore", "Bangalore"),
                        new Flight("2", "Indigo", "13-01-2025T13:25:00", "13-01-2025T17:10:00", "Singapore", "Bangalore")));
        Mono<BaseResponse> responseMono = flightManagementService.searchFlights("Singapore", "Bangalore");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();

    }

    @Test
    void searchFlights_ThrowException() {
        when(flightRepo.findByDepLocationAndDestination(anyString(), anyString())).thenReturn(Flux.error(new CustomException("error", HTTP_500)));
        Mono<BaseResponse> responseMono = flightManagementService.searchFlights("Singapore", "Bangalore");
        StepVerifier.create(responseMono)
                .expectErrorMatches(res -> res instanceof CustomException && ((CustomException) res).getErrorCode().equals(HTTP_500)).verify();

    }

    @Test
    void searchFlights_error() {
        when(flightRepo.findByDepLocationAndDestination(anyString(), anyString())).thenReturn(Flux.just(new Flight()));
        Mono<BaseResponse> responseMono = flightManagementService.searchFlights("Singapore", "Bangalore");
        StepVerifier.create(responseMono)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();

    }
}
