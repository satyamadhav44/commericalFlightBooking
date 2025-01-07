package com.flight.commercialFlight.constants;

public class Paths {
    public static final String ROOT = "/v1/api";

    /* Flight  */
    public static final String FETCH_FLIGHT_DETAIL = "/flight/fetchDetails/{flight_id}";
    public static final String FETCH_FLIGHT_ALL = "/flight/fetchDetails";
    public static final String ADD_NEW_PLANE = "/flight/add";
    public static final String UPDATE_PLANE_INFO = "/flight/update/{flight_id}";
    /* Passenger */
    public static final String FETCH_PASENG_ID = "/passenger/fetchDetails/{passenger_id}";
    public static final String REGISTER_PASSENGER = "/passenger/register";
    public static final String UPDATE_PASSENGER = "/passenger/update";
    public static final String FETCH_PASSENGER_ALL = "/passenger/fetchDetails";
    /* Booking*/
    //todo list all the api paths for handling bookings.
    public static final String FETCH_BOOKING_ID = "/booking/details/{booking_id}";


}
