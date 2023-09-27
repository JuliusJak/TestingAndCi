package com.example.testingandci.unitTesting;

import com.example.testingandci.controller.BookingController;
import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.service.ActiveBookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingControllerUnitTests {

    @Mock
    private ActiveBookingService activeBookingService;
    @InjectMocks
    private BookingController bookingController;

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

    @Test
    public void testDeleteAccountService() {
        long userId = 1L;

        bookingController.deleteBookingById(userId);

        verify(activeBookingService, times(1)).deleteBooking(userId);
    }
}
