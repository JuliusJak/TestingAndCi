package com.example.testingandci.controller;

import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.service.ActiveBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("bookings/")
public class BookingController {

    @Autowired
    private ActiveBookingService activeBookingService;
    @Autowired
    private  PaymentHistoryController paymentHistoryController;

    @PostMapping("create")
    public ActiveBookings createBooking(
            @RequestParam("userId") long userId,
            @RequestParam("routeId") long routeId) throws AccountNotFoundException {


        ActiveBookings activeBookings = ActiveBookings.builder()
                .userId(userId)
                .routeId(routeId)
                .build();

        paymentHistoryController.autoCreatePayment(userId, routeId);
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
    public void deleteBookingById(@PathVariable Long id) {
        if (id == null){
            throw new NullPointerException("parameter id can not be null");
        } else {
            activeBookingService.deleteBooking(id);
        }    }

}
