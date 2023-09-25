package com.example.testingandci.service;

import com.example.testingandci.model.Account;

public interface IAccountService {
    Account updateAccount(Account oldAccount);

    Account saveAccount(Account newAccount);

    void deleteAccount(Long accountId);

    Account fetchedAccount(Long accountId);
}