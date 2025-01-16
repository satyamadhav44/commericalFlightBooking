package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.dto.PassengerDetails;
import com.flight.commercialFlight.entity.Booking;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.BookingRepo;
import com.flight.commercialFlight.repository.FlightRepo;
import com.flight.commercialFlight.service.FlightManagementService;
import com.flight.commercialFlight.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.flight.commercialFlight.constants.MessagesAndCodes.HTTP_200;
import static com.flight.commercialFlight.constants.MessagesAndCodes.HTTP_500;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;
    @Mock
    private FlightManagementService flightManagementService;
    @Mock
    private PassengerService passengerService;
    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private FlightRepo flightRepo;

    @Test
    public void makeFlightBookingTest_Success() throws InterruptedException {
        //arrange
        Booking booking = new Booking();
        PassengerDetails passengerDetails = new PassengerDetails();
        FlightDetails flightDetails = new FlightDetails();
        when(flightManagementService.fetchFlightbyId(anyString())).thenReturn(Mono.just(new BaseResponse(flightDetails, HTTP_200)));
        when(passengerService.getPassengerInfo(anyString())).thenReturn(Mono.just(new BaseResponse(passengerDetails, HTTP_200)));
        when(bookingRepo.insert(any(Booking.class))).thenReturn(Mono.just(booking));
        //act
        Mono<BaseResponse> responseMono = bookingService.makeFlightBooking("1", "2");
        //assert
        StepVerifier.create(responseMono)
                .expectNextMatches(result -> result.getStatusCode().equals(HTTP_200))
                .verifyComplete();
    }

    @Test
    void makeFlightBookingTest_whenFlightNotFound() throws InterruptedException {
        //arrange
        PassengerDetails passengerDetails = new PassengerDetails();
        when(flightManagementService.fetchFlightbyId(anyString())).thenReturn(Mono.just(new BaseResponse(ErrorEnum.F101.getMessage(), HTTP_500)));
        when(passengerService.getPassengerInfo(anyString())).thenReturn(Mono.just(new BaseResponse(passengerDetails, HTTP_200)));
        //act
        Mono<BaseResponse> responseMono = bookingService.makeFlightBooking("1", "1");
        //assert
        StepVerifier.create(responseMono)
                .expectErrorMatches(thrown -> thrown instanceof CustomException && ((CustomException) thrown).getErrorCode().equals(HTTP_500)).verify();
    }

    @Test
    void makeFlightBookingTest_whenPassengerNotFound() throws InterruptedException {
        FlightDetails flightDetails = new FlightDetails();
        when(flightManagementService.fetchFlightbyId(anyString())).thenReturn(Mono.just(new BaseResponse(flightDetails, HTTP_200)));
        when(passengerService.getPassengerInfo(anyString())).thenReturn(Mono.just(new BaseResponse(ErrorEnum.F101.getMessage(), HTTP_500)));
        Mono<BaseResponse> responseMono = bookingService.makeFlightBooking("1", "1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(throwable -> throwable instanceof CustomException && ((CustomException) throwable).getErrorCode().equals(HTTP_500)).verify();

    }

    @Test
    void cancelFlightBookingTest() {
        when(bookingRepo.deleteById(anyString())).thenReturn(Mono.empty());
        Mono<BaseResponse> responseMono = bookingService.cancelFlightBooking("1");
        StepVerifier.create(responseMono)
                .expectComplete();

    }

    @Test
    void viewBookingByFlight_Success() {
        FlightDetails flightDetails = new FlightDetails();
        when(flightManagementService.fetchFlightbyId(anyString())).thenReturn(Mono.just(new BaseResponse(flightDetails, HTTP_200)));
        when(bookingRepo.findAllByFlightNumber(anyString())).thenReturn(Flux.just(new Booking[]{new Booking()}));
        Mono<BaseResponse> responseMono = bookingService.viewBookingByFlight("1");
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getStatusCode().equals(HTTP_200)).verifyComplete();
    }

    @Test
    void viewBookingByFlight_ThrowsException() {
        FlightDetails flightDetails = new FlightDetails();
        when(flightManagementService.fetchFlightbyId(anyString())).thenReturn(Mono.just(new BaseResponse(flightDetails, HTTP_200)));
        when(bookingRepo.findAllByFlightNumber(anyString())).thenReturn(Flux.error(new CustomException("error", HTTP_500)));
        Mono<BaseResponse> responseMono = bookingService.viewBookingByFlight("1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(errRes -> errRes instanceof CustomException && ((CustomException) errRes).getErrorCode().equals(ErrorEnum.F102.name())).verify();
    }

    @Test
    void viewBookingByFlight_NoBookingFoundOnFlight() {
        when(flightManagementService.fetchFlightbyId(anyString())).thenReturn(Mono.error(new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F102.name())));
        Mono<BaseResponse> responseMono = bookingService.viewBookingByFlight("1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(throwable -> throwable instanceof CustomException && throwable.getMessage().equals(ErrorEnum.F101.getMessage())).verify();
    }

    @Test
    void viewBookingByPassenger_Success() {
        PassengerDetails passengerDetails = new PassengerDetails();
        when(passengerService.getPassengerInfo(anyString())).thenReturn(Mono.just(new BaseResponse(passengerDetails, HTTP_200)));
        when(bookingRepo.findAllByPassengerId(anyString())).thenReturn(Flux.just(new Booking[]{new Booking()}));
        Mono<BaseResponse> responseMono = bookingService.viewBookingByPassenger("1");
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> response.getStatusCode().equals(HTTP_200)).verifyComplete();
    }

    @Test
    void viewBookingByPassenger_ThrowsException() {
        PassengerDetails passengerDetails = new PassengerDetails();
        when(passengerService.getPassengerInfo(anyString())).thenReturn(Mono.just(new BaseResponse(passengerDetails, HTTP_200)));
        when(bookingRepo.findAllByPassengerId(anyString())).thenReturn(Flux.error(new CustomException("error", HTTP_500)));
        Mono<BaseResponse> responseMono = bookingService.viewBookingByPassenger("1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(errRes -> errRes instanceof CustomException && ((CustomException) errRes).getErrorCode().equals(ErrorEnum.F102.name())).verify();
    }

    @Test
    void viewBookingByPassenger_NoBookingFoundOnFlight() {
        when(passengerService.getPassengerInfo(anyString())).thenReturn(Mono.error(new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F102.name())));
        Mono<BaseResponse> responseMono = bookingService.viewBookingByPassenger("1");
        StepVerifier.create(responseMono)
                .expectErrorMatches(throwable -> throwable instanceof CustomException && throwable.getMessage().equals(ErrorEnum.F101.getMessage())).verify();
    }

    @Test
    void getAlltheBookings_Success() {
        Booking booking = new Booking();
        when(bookingRepo.findAll()).thenReturn(Flux.just(booking));
        Mono<BaseResponse> result = bookingService.getAlltheBookings();
        StepVerifier.create(result)
                .expectNextMatches(res -> res.getStatusCode().equals(HTTP_200)).verifyComplete();
    }


}
