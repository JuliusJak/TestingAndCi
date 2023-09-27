package com.example.testingandci.service;

import com.example.testingandci.model.PaymentHistory;

import java.util.List;

public interface IPaymentHistoryService {
    List<PaymentHistory> fetchPaymentHistory(Long accountId);
    PaymentHistory createPayment(PaymentHistory newPayment);


}