package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.service.ActiveBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bookings/")
public class BookingController {

    @Autowired
    private ActiveBookingService activeBookingService;

    @PostMapping("create")
    public ActiveBookings createBooking() {
        ActiveBookings activeBookings = ActiveBookings.builder()
                .routeId(1)
                .username("testing")
                .build();

        return activeBookingService.createNewBooking(activeBookings);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<ActiveBookings> getAccountById(@PathVariable long id) {
        ActiveBookings activeBookings = activeBookingService.fetchActiveBookingList(id);
        if (activeBookings != null) {
            return ResponseEntity.ok(activeBookings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        activeBookingService.deleteBooking(id);
    }

}
