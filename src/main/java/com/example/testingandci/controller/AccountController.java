package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/create/account")
    public Account saveAccount() {
        Account account = Account.builder()
                .username("Josef")
                .contactInfo("112")
                .accountType("ADMIN")
                .paymentInfo(112)
                .build();
        return accountService.saveAccount(account);
    }
    @GetMapping("/get/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable long id) {
        Account account = accountService.fetchedAccount(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/update/{id}")
    public Account updateAccount(@PathVariable long id)
            throws AccountNotFoundException {
        Account existingAccount = accountService.fetchedAccount(id);
        if (existingAccount == null) {
            throw new AccountNotFoundException("Account with ID " + id + " not found");
        }

        existingAccount.setUsername("Adam");
        existingAccount.setContactInfo("321");
        existingAccount.setAccountType("USER");
        existingAccount.setPaymentInfo(999);

        return accountService.saveAccount(existingAccount);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(id);
    }
}