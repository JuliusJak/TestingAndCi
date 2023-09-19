package com.example.testingandci.service;

import com.example.testingandci.model.Account;

public interface IAccountService {
    /*Create new account*/
    Account saveAccount(Account newAccount);

    /*Update field*/
    Account updateAccount(Account oldAccount);

    /*Delete account*/
    void deleteAccount(Long accountId);

    /*Fetch account*/
    Account fetchedAccount(Long accountId);
}