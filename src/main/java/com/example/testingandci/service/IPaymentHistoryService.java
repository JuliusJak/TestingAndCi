package com.example.testingandci.service;

import com.example.testingandci.model.PaymentsHistory;

public interface IPaymentHistoryService {
    /*Fetch object*/
    PaymentsHistory paymentHistory(Long accountId);
}