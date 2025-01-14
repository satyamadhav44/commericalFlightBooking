package com.flight.commercialFlight.constants;

public class Paths {
    public static final String ROOT = "/v1/api";

    /* Flight  */
    public static final String FETCH_FLIGHT_DETAIL = "/flight/fetchDetails/{flight_id}";
    public static final String FETCH_FLIGHT_ALL = "/flight/fetchDetails";
    public static final String ADD_NEW_PLANE = "/flight/add";
    public static final String UPDATE_PLANE_INFO = "/flight/update/{flight_id}";
    public static final String SEARCH_FLIGHT = "/flight/{departure}/{destination}";
    public static final String FLIGHT_BY_DATE_TIME = "/flight/{datetime}";
    /* Passenger */
    public static final String FETCH_PASENG_ID = "/passenger/fetchDetails/{passenger_id}";
    public static final String REGISTER_PASSENGER = "/passenger/register";
    public static final String UPDATE_PASSENGER = "/passenger/update";
    public static final String FETCH_PASSENGER_ALL = "/passenger/fetchDetails";
    /* Booking*/
    //todo list all the api paths for handling bookings.
    public static final String CREATE_BOOKING = "/booking/create/{flight_id}/{passenger_id}";
    public static final String ALL_BOOKING = "/booking/all/details";
    public static final String BOOKING_BY_FLIGHT_NUM = "/booking/flightNo/{flight_id}";
    public static final String CANCLE_BOOKING = "/booking/{booking_id}";
    public static final String BOOKING_BY_PASSENGER_NUM = "/booking/passengerNo/{passenger_id}";


}
