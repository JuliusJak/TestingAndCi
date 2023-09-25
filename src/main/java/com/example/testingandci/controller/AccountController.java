package com.example.testingandci.controller;

import com.example.testingandci.model.Account;
import com.example.testingandci.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

import static org.springframework.util.ObjectUtils.isEmpty;

@RestController
@RequestMapping("account/")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PostMapping("create")
    public Account saveAccount(
            @RequestParam("username") String username,
            @RequestParam("contactInfo") String contactInfo,
            @RequestParam("accountType") String accountType,
            @RequestParam("paymentInfo") int paymentInfo) {

        if (username.isEmpty()
                || contactInfo.isEmpty()
                || accountType.isEmpty()) {
            throw new IllegalArgumentException("All parameters must be provided");
        } else if (paymentInfo < 0) {
            throw new IllegalArgumentException("PaymentInfo can not be a negative number or 0");
        }


        Account account = Account.builder()
                .username(username)
                .contactInfo(contactInfo)
                .accountType(accountType)
                .paymentInfo(paymentInfo)
                .build();
        return accountService.saveAccount(account);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable long id) {
        Account account = accountService.fetchedAccount(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("update/{id}")
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

    @DeleteMapping("delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        accountService.deleteAccount(id);
    }
}