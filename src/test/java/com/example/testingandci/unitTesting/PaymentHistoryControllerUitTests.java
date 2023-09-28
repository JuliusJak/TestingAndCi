package com.example.testingandci.unitTesting;

import com.example.testingandci.controller.PaymentHistoryController;
import com.example.testingandci.exceptions.PaymentNotFoundException;
import com.example.testingandci.model.Account;
import com.example.testingandci.model.PaymentHistory;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.PaymentHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @ParameterizedTest
    @ValueSource(longs = { 1L, 2L })
    public void testGetHistoryById(Long userId) throws PaymentNotFoundException {

        if (userId.equals(1L)) {
            PaymentHistory mockPaymentHistory = new PaymentHistory();
            when(paymentHistoryService.fetchPaymentHistoryById(userId)).thenReturn(mockPaymentHistory);

            ResponseEntity<PaymentHistory> responseEntity = paymentHistoryController.getHistoryById(userId);

            assertEquals(mockPaymentHistory, responseEntity.getBody());
            assertEquals(200, responseEntity.getStatusCode().value());
        } else {
            when(paymentHistoryService.fetchPaymentHistoryById(userId)).thenThrow(new PaymentNotFoundException("Payment not found"));

            assertThrows(PaymentNotFoundException.class, () -> {
                paymentHistoryController.getHistoryById(userId);
            });
        }
    }

    @ParameterizedTest
    @CsvSource({"1", ",", "3", ","})
    public void testDeletePayment(Long id) {

        if (id == null) {
            assertThrows(NullPointerException.class, () -> paymentHistoryController.deletePayment(id));
        } else {
            paymentHistoryController.deletePayment(id);
            verify(paymentHistoryService, times(1)).deletePayment(id);
        }
    }
}
