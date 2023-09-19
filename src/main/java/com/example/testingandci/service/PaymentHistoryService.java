package com.example.testingandci.service;

import com.example.testingandci.model.PaymentsHistory;
import com.example.testingandci.repository.IPaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoryService implements IPaymentHistoryService{
    @Autowired
    IPaymentHistoryRepository repository;

    @Override
    public PaymentsHistory paymentHistory(Long accountId) {
        return null;
    }
}