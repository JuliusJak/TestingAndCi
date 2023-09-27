package com.example.testingandci.unitTesting;

import com.example.testingandci.controller.BookingController;
import com.example.testingandci.model.Account;
import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.ActiveBookingService;
import com.example.testingandci.service.PaymentHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerUnitTests {

    @Mock
    private ActiveBookingService activeBookingService;
    @Mock
    private PaymentHistoryService paymentHistoryService;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private BookingController bookingController;


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

        bookingController.createPayment(userId, routeId);

        verify(paymentHistoryService, times(1)).createPayment(argThat(paymentHistory -> paymentHistory.getAccountId() == userId
                && paymentHistory.getRouteId() == routeId
                && paymentHistory.getUsername().equals(username)));
    }
    @Test
    public void testCreateBooking() {
        long userId = 1L;
        long routeId = 2L;
        ActiveBookings expectedBooking = ActiveBookings.builder()
                .userId(userId)
                .routeId(routeId)
                .build();

        when(activeBookingService.createNewBooking(any(ActiveBookings.class)))
                .thenReturn(expectedBooking);

        ActiveBookings actualBooking = bookingController.createBooking(userId, routeId);

        verify(activeBookingService, times(1)).createNewBooking(any(ActiveBookings.class));
        assertEquals(expectedBooking, actualBooking);
    }

    @Test
    public void testGetBookingsByUserId() {

        long userId = 1L;
        List<ActiveBookings> activeBookings = new ArrayList<>();
        activeBookings.add(ActiveBookings.builder().userId(userId).build());

        when(activeBookingService.fetchActiveBookingsByUserId(userId))
                .thenReturn(activeBookings);

        ResponseEntity<List<ActiveBookings>> response = bookingController.getBookingsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(activeBookings, response.getBody());
    }

    @ParameterizedTest
    @CsvSource({"1", "2", "3", ","})
    public void testDeleteBooking(Long id) {
        if (id == null) {
            assertThrows(NullPointerException.class, () -> bookingController.deleteBookingById(id));
        } else {
            bookingController.deleteBookingById(id);
            verify(activeBookingService, times(1)).deleteBooking(id);
        }
    }
}
