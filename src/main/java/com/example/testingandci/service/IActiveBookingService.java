package com.example.testingandci.service;

import com.example.testingandci.model.ActiveBookings;

public interface IActiveBookingService {
    /*Fetch object*/
    ActiveBookings fetchActiveBookingList(Long accountId);

    /*Create new booking*/
    ActiveBookings createNewBooking(ActiveBookings activeBookings);

    /*Delete a booking*/
    void deleteBooking(Long bookingId);
}