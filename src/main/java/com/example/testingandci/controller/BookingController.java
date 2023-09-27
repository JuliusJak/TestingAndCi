package com.example.testingandci.controller;

import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.service.ActiveBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings/")
public class BookingController {

    @Autowired
    private ActiveBookingService activeBookingService;

    @PostMapping("create")
    public ActiveBookings createBooking(
            @RequestParam("userId") long userId,
            @RequestParam("routeId") long routeId) {


        ActiveBookings activeBookings = ActiveBookings.builder()
                .userId(userId)
                .routeId(routeId)
                .build();

        return activeBookingService.createNewBooking(activeBookings);
    }
    @GetMapping("get/{userId}")
    public ResponseEntity<List<ActiveBookings>> getBookingsByUserId(@PathVariable long userId) {
        List<ActiveBookings> activeBookings = activeBookingService.fetchActiveBookingsByUserId(userId);

        if (!activeBookings.isEmpty()) {
            return ResponseEntity.ok(activeBookings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{id}")
    public void deleteBookingById(@PathVariable long id) {
        activeBookingService.deleteBooking(id);
    }

}
