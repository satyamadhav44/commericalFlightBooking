package com.flight.commercialFlight.service.impl;

import com.flight.commercialFlight.constants.ErrorEnum;
import com.flight.commercialFlight.dto.BaseResponse;
import com.flight.commercialFlight.dto.BookingDetails;
import com.flight.commercialFlight.entity.Booking;
import com.flight.commercialFlight.exception.CustomException;
import com.flight.commercialFlight.repository.BookingRepo;
import com.flight.commercialFlight.repository.FlightRepo;
import com.flight.commercialFlight.repository.PassengerRepo;
import com.flight.commercialFlight.service.BookingService;
import com.flight.commercialFlight.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.flight.commercialFlight.constants.MessagesAndCodes.BOOKING_CANCLE;
import static com.flight.commercialFlight.constants.MessagesAndCodes.HTTP_200;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private FlightRepo flightRepo;
    @Autowired
    private PassengerRepo passengerRepo;

    @Override
    public BaseResponse makeFlightBooking(String flightNo, String passengerId) throws InterruptedException {
        Booking booking = new Booking();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(() -> {
            try {
                flightRepo.findById(flightNo).ifPresentOrElse(
                        booking::setFlight,
                        () -> {
                            throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                        }
                );
            } catch (CustomException ex) {
                throw new CustomException(ex.getMessage(), ex.getErrorCode());
            }
        });
        service.submit(() -> {
            try {
                passengerRepo.findById(passengerId).ifPresentOrElse(
                        booking::setPassenger,
                        () -> {
                            throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                        }
                );
            } catch (CustomException ex) {
                throw new CustomException(ex.getMessage(), ex.getErrorCode());
            }
        });
        service.shutdown();
        service.awaitTermination(100, TimeUnit.MILLISECONDS);
        booking.setBookingDate(new Date());
        bookingRepo.insert(booking);
        BookingDetails bookingDetails = Converter.entityToDto(booking);
        return BaseResponse.builder().data(bookingDetails).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse cancelFlightBooking(String bookingId) {
        bookingRepo.deleteById(bookingId);
        return BaseResponse.builder().data(BOOKING_CANCLE).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse viewBookingByFlight(String flightNo) {
        AtomicReference<List<Booking>> booking = new AtomicReference<>(new ArrayList<>());
        flightRepo.findById(flightNo).ifPresentOrElse(
                value -> {
                    booking.set(bookingRepo.findAll().stream().filter(e -> e.getFlight().getNumber().equals(flightNo)).collect(Collectors.toList()));
                },
                () -> {
                    throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                }
        );
        return BaseResponse.builder().data(booking.get()).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse viewBookingByPassenger(String passId) {
        AtomicReference<List<Booking>> booking = new AtomicReference<>(new ArrayList<>());
        passengerRepo.findById(passId).ifPresentOrElse(
                value -> {
                    booking.set(bookingRepo.findAll().stream().filter(e -> e.getPassenger().getId().equals(passId)).collect(Collectors.toList()));
                },
                () -> {
                    throw new CustomException(ErrorEnum.F101.getMessage(), ErrorEnum.F101.name());
                }
        );
        return BaseResponse.builder().data(booking.get()).statusCode(HTTP_200).build();
    }

    @Override
    public BaseResponse getAlltheBookings() {
        List<BookingDetails> bookingDetailsList = new ArrayList<>();
        bookingRepo.findAll().forEach(each -> {
            bookingDetailsList.add(Converter.entityToDto(each));
        });
        return BaseResponse.builder().data(bookingDetailsList).statusCode(HTTP_200).build();
    }

}
