package com.example.testingandci.service;

import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.repository.IActiveBookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActiveBookingService implements IActiveBookingService{
    @Autowired
    IActiveBookingsRepository repository;

    @Override
    public List<ActiveBookings> fetchActiveBookingsByUserId(long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public ActiveBookings createNewBooking(ActiveBookings activeBookings) {
        return repository.save(activeBookings);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        repository.deleteById(bookingId);

    }
}