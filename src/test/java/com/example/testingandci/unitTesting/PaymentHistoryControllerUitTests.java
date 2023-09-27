package com.example.testingandci.unitTesting;

import com.example.testingandci.controller.PaymentHistoryController;
import com.example.testingandci.model.Account;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.PaymentHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentHistoryControllerUitTests {

    @Mock
    private PaymentHistoryService paymentHistoryService;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private PaymentHistoryController paymentHistoryController;


    @Test
    public void testCreatePayment() {

        long userId = 1L;
        long routeId = 2L;
        String username = "testUser";
        Account mockAccount = Account.builder()
                .id(userId)
                .username(username)
                .build();

        when(accountService.fetchedAccount(userId)).thenReturn(mockAccount);

        paymentHistoryController.createPayment(userId, routeId);

        verify(paymentHistoryService, times(1))
                .createPayment(argThat(paymentHistory -> paymentHistory.getAccountId() == userId
                && paymentHistory.getRouteId() == routeId
                && paymentHistory.getUsername().equals(username)));
    }
}
