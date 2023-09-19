package com.example.testingandci.service;

import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.repository.IActiveBookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActiveBookingService implements IActiveBookingService{
    @Autowired
    IActiveBookingsRepository repository;

    @Override
    public ActiveBookings fetchActiveBookingList(Long accountId) {
        return null;
    }

    @Override
    public ActiveBookings createNewBooking(ActiveBookings activeBookings) {
        return null;
    }

    @Override
    public void deleteBooking(Long bookingId) {

    }
}