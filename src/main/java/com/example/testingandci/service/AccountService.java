package com.example.testingandci.service;

import com.example.testingandci.model.Account;
import com.example.testingandci.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository repository;

    @Override
    public Account saveAccount(Account newAccount) {
        return repository.save(newAccount);
    }

    @Override
    public Account updateAccount(Account oldAccount) {
        return null;
    }

    @Override
    public void deleteAccount(Long accountId) {

    }

    @Override
    public Account fetchedAccount(Long accountId) {
        return null;
    }
}