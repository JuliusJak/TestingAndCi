package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.model.TransportationRoute;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.ActiveBookingService;
import com.example.testingandci.service.TransportationRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings/")
public class BookingController {

    @Autowired
    private ActiveBookingService activeBookingService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransportationRouteService transportationRouteService;

    @PostMapping("create")
    public ActiveBookings createBooking(
            @RequestParam("userId") long userId,
            @RequestParam("routeId") long routeId) {

        Account account = accountService.fetchedAccount(userId);
        String username = account.getUsername();

        ActiveBookings activeBookings = ActiveBookings.builder()
                .userId(userId)
                .username(username)
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

    //get all active booking

    @DeleteMapping("delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        activeBookingService.deleteBooking(id);
    }

}
