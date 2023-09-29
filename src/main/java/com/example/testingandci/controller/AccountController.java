package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("account/")
public class AccountController {
    @Autowired
    private AccountService accountService;
    //TODO Check for valid params such as contact info, account type and payment info
    // contact info needs to be a valid email or 10 digits
    // payment info must be a SWISH account with a correct amount of digits
    @PostMapping("create")
    public Account saveAccount(
            @RequestParam("username") String username,
            @RequestParam("contactInfo") String contactInfo,
            @RequestParam("accountType") String accountType,
            @RequestParam("paymentInfo") Integer paymentInfo) {


        if (username == null
                || contactInfo == null
                || accountType == null
                || paymentInfo == null) {

            throw new NullPointerException("Parameter can not be null");
        }
        else if (username.isEmpty()
                || contactInfo.isEmpty()
                || accountType.isEmpty()) {

            throw new IllegalArgumentException("All parameters must be provided");

        } else if (paymentInfo <= 0) {
            throw new IllegalArgumentException("PaymentInfo must be greater than 0");
        } else if (!isValidAccountType(accountType)) {
            throw new IllegalArgumentException("AccountType must be either USER, ADMIN or PROVIDER");
        }

        Account account = Account.builder()
                .username(username)
                .contactInfo(contactInfo)
                .accountType(accountType)
                .paymentInfo(paymentInfo)
                .build();
        return accountService.saveAccount(account);
    }
    public boolean isValidAccountType(String accountType) {
        return "USER".equals(accountType)
                || "ADMIN".equals(accountType)
                || "PROVIDER".equals(accountType);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable long id) throws AccountNotFoundException {
        Account account = accountService.fetchedAccount(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            throw new AccountNotFoundException("Account with ID " + id + " not found");
        }
    }
    @PatchMapping("update/{id}")
    public Account updateAccount(
            @PathVariable long id,
            @RequestParam("username") String username,
            @RequestParam("contactInfo") String contactInfo,
            @RequestParam("paymentInfo") int paymentInfo)
            throws AccountNotFoundException {
        Account existingAccount = accountService.fetchedAccount(id);
        if (existingAccount == null) {
            throw new AccountNotFoundException("Account with ID " + id + " not found");
        }

        if (!username.isEmpty()) {
            existingAccount.setUsername(username);
        }
        if (!contactInfo.isEmpty()) {
            existingAccount.setContactInfo(contactInfo);
        }

        if (paymentInfo > 0) {
            existingAccount.setPaymentInfo(paymentInfo);
        } else {
            throw new IllegalArgumentException("PaymentInfo can not be a negative number or 0");
        }

        return accountService.saveAccount(existingAccount);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAccount(@PathVariable Long id) {
        if (id == null){
            throw new NullPointerException("parameter id can not be null");
        } else {
            accountService.deleteAccount(id);
        }
    }
}