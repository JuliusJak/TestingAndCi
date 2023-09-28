package com.example.testingandci.controller;

import com.example.testingandci.model.PaymentHistory;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment/")
public class PaymentHistoryController {
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @Autowired
    private AccountService accountService;

    //TODO Add edgecases
    public void autoCreatePayment(long userId, long routeId){

        String username = accountService.fetchedAccount(userId).getUsername();

        PaymentHistory newPayment = PaymentHistory.builder()
                .accountId(userId)
                .routeId(routeId)
                .username(username)
                .build();
        paymentHistoryService.createPayment(newPayment);
    }

    @PostMapping("create")
    public void createPayment(
            @RequestParam("userId") Long userId,
            @RequestParam("routeId") Long routeId){

        autoCreatePayment(userId,routeId);

    }
    // TODO finish getHistoryById
    @GetMapping("get/id")
    public PaymentHistory getHistoryById(
            @RequestParam("userId") Long userId){
        return null;
    }

    @DeleteMapping("delete")
    public void deletePayment(@RequestParam Long id) {

        if (id == null){
            throw new NullPointerException("parameter id can not be null");
        } else {
            paymentHistoryService.deletePayment(id);
        }
    }}
