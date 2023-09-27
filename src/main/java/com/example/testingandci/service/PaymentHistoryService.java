package com.example.testingandci.service;

import com.example.testingandci.model.PaymentHistory;
import com.example.testingandci.repository.IPaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentHistoryService implements IPaymentHistoryService{
    @Autowired
    IPaymentHistoryRepository repository;

    @Override
    public List<PaymentHistory> fetchPaymentHistory(Long accountId) {
        return repository.findAllByAccountId(accountId);
    }

    @Override
    public PaymentHistory createPayment(PaymentHistory newPayment) {
        return repository.save(newPayment);
    }
}