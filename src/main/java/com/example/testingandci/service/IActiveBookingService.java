package com.example.testingandci.service;

import com.example.testingandci.model.ActiveBookings;

import java.util.List;

public interface IActiveBookingService {

    List<ActiveBookings> fetchActiveBookingsByUserId(long userId);

    ActiveBookings createNewBooking(ActiveBookings activeBookings);

    void deleteBooking(Long bookingId);
}