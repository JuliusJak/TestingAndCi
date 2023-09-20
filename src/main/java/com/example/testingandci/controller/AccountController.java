package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    // Save operation
    @PostMapping("/create_account")
    public Account saveAccount() {
        Account account = Account.builder()
                .username("Josef")
                .contactInfo("112")
                .accountType("ADMIN")
                .paymentInfo(112)
                .build();
        return accountService.saveAccount(account);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(id);
    }
}