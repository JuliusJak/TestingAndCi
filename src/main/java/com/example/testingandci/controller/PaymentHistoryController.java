package com.example.testingandci.controller;

import com.example.testingandci.exceptions.PaymentNotFoundException;
import com.example.testingandci.model.Account;
import com.example.testingandci.model.PaymentHistory;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("payment/")
public class PaymentHistoryController {
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @Autowired
    private AccountService accountService;

    //TODO Add edgecases
    // add mocked payment response
    public PaymentHistory autoCreatePayment(long userId, long routeId){

        String username = accountService.fetchedAccount(userId).getUsername();

        PaymentHistory newPayment = PaymentHistory.builder()
                .accountId(userId)
                .routeId(routeId)
                .username(username)
                .build();
        paymentHistoryService.createPayment(newPayment);
        return newPayment;
    }

    @PostMapping("create")
    public PaymentHistory createPayment(
            @RequestParam("userId") Long userId,
            @RequestParam("routeId") Long routeId){


        return autoCreatePayment(userId, routeId);
    }
    @GetMapping("get/id")
    public ResponseEntity<PaymentHistory> getHistoryById(
            @RequestParam("userId") Long userId) throws PaymentNotFoundException {
        PaymentHistory paymentHistory = paymentHistoryService.fetchPaymentHistoryById(userId);
        if (paymentHistory != null) {
            return ResponseEntity.ok(paymentHistory);
        } else {
            throw new PaymentNotFoundException("Payment with ID " + userId + " not found");
        }
    }

    @DeleteMapping("delete")
    public void deletePayment(@RequestParam Long id) {

        if (id == null){
            throw new NullPointerException("parameter id can not be null");
        } else {
            paymentHistoryService.deletePayment(id);
        }
    }}
