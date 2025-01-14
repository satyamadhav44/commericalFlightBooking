package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.BookingDetails;
import com.flight.commercialFlight.dto.FlightDetails;
import com.flight.commercialFlight.dto.PassengerDetails;
import com.flight.commercialFlight.entity.Booking;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.BookingRepo;
import com.flight.commercialFlight.service.BookingService;
import com.flight.commercialFlight.service.FlightManagementService;
import com.flight.commercialFlight.service.PassengerService;
import com.flight.commercialFlight.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.flight.commercialFlight.constants.MessagesAndCodes.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private FlightManagementService flightManagementService;

    @Override
    public Mono<BaseResponse> makeFlightBooking(String flightNo, String passengerId) throws InterruptedException {
        return flightManagementService.fetchFlightbyId(flightNo).mapNotNull(
                        flight -> {
                            if (flight.getStatusCode().equals(HTTP_200)) {
                                return Converter.dtoToEntity((FlightDetails) flight.getData());
                            } else throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                        }
                ).zipWith(passengerService.getPassengerInfo(passengerId).mapNotNull(passenger -> {
                    if (passenger.getStatusCode().equals(HTTP_200)) {
                        return Converter.dtoToEntity((PassengerDetails) passenger.getData());
                    } else throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                }))
                .flatMap(tuples -> {
                    Booking booking = new Booking();
                    booking.setBookingDate(new Date());
                    booking.setFlight(tuples.getT1());
                    booking.setPassenger(tuples.getT2());
                    return bookingRepo.insert(booking).mapNotNull(value -> BaseResponse.builder().data(Converter.entityToDto(booking)).statusCode(HTTP_200).build());
                }).doOnError(err -> {
                    throw new CustomException(err.getMessage(), HTTP_500);
                });
    }

    @Override
    public Mono<BaseResponse> cancelFlightBooking(String bookingId) {
        return bookingRepo.deleteById(bookingId)
                .map(valur -> BaseResponse.builder().data(BOOKING_CANCLE).statusCode(HTTP_200).build());
    }

    @Override
    public Mono<BaseResponse> viewBookingByFlight(String flightNo) {
        return flightManagementService.fetchFlightbyId(flightNo).flatMap(flight -> {
                    if (flight.getStatusCode().equals(HTTP_200)) {
                        return bookingRepo.findAllByFlightNumber(flightNo).collectList().mapNotNull(data1 -> {
                            List<BookingDetails> bookingDetailsList = new ArrayList<>();
                            data1.forEach(each -> {
                                bookingDetailsList.add(Converter.entityToDto(each));
                            });
                            return BaseResponse.builder().data(bookingDetailsList).statusCode(HTTP_200).build();
                        }).doOnError(err -> {
                            throw new CustomException(err.getMessage(), HTTP_500);
                        });
                    } else throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                }
        ).doOnError(err -> {
            throw new CustomException(err.getMessage(), ErrorEnum.F102.name());
        });
    }

    @Override
    public Mono<BaseResponse> viewBookingByPassenger(String passId) {
        return passengerService.getPassengerInfo(passId).flatMap(pass -> {
                    if (pass.getStatusCode().equals(HTTP_200)) {
                        return bookingRepo.findAllByPassengerId(passId).collectList().mapNotNull(data1 -> {
                            List<BookingDetails> bookingDetailsList = new ArrayList<>();
                            data1.forEach(each -> {
                                bookingDetailsList.add(Converter.entityToDto(each));
                            });
                            return BaseResponse.builder().data(bookingDetailsList).statusCode(HTTP_200).build();
                        }).doOnError(err -> {
                            throw new CustomException(err.getMessage(), HTTP_500);
                        });
                    } else throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                }
        ).doOnError(err -> {
            throw new CustomException(err.getMessage(), ErrorEnum.F102.name());
        });
    }

    @Override
    public Mono<BaseResponse> getAlltheBookings() {
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        return bookingRepo.findAll().collectSortedList().map(listofBooking -> {
            listofBooking.forEach(each -> {
                bookingDetailsList.add(Converter.entityToDto(each));
            });
            return BaseResponse.builder().data(bookingDetailsList).statusCode(HTTP_200).build();
        });
    }

}
