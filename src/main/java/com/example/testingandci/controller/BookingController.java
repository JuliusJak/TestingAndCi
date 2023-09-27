package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.model.PaymentHistory;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.ActiveBookingService;
import com.example.testingandci.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookings/")
public class BookingController {

    @Autowired
    private ActiveBookingService activeBookingService;
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @Autowired
    private AccountService accountService;

    public void createPayment(long userId, long routeId){

        String username = accountService.fetchedAccount(userId).getUsername();

        PaymentHistory newPayment = PaymentHistory.builder()
                .accountId(userId)
                .routeId(routeId)
                .username(username)
                .build();
        paymentHistoryService.createPayment(newPayment);
    }
    @PostMapping("create")
    public ActiveBookings createBooking(
            @RequestParam("userId") long userId,
            @RequestParam("routeId") long routeId) {


        ActiveBookings activeBookings = ActiveBookings.builder()
                .userId(userId)
                .routeId(routeId)
                .build();

        // if routeId != routes that exists or route is not currently available throw exception
        createPayment(userId,routeId);
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
