package com.example.testingandci.endToEndTesting;

import com.example.testingandci.controller.BookingController;
import com.example.testingandci.model.Account;
import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.service.AccountService;
import com.example.testingandci.service.ActiveBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookingControllerEndToEndTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActiveBookingService activeBookingService;
    @MockBean
    private AccountService accountService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testCreateBookingWithExistingAccount() throws Exception {
        // Mock the service response
        ActiveBookings activeBooking = new ActiveBookings();
        when(activeBookingService.createNewBooking(any(ActiveBookings.class))).thenReturn(activeBooking);

        // Mock the AccountService to return a valid Account object for userId 1
        when(accountService.fetchedAccount(1L)).thenReturn(new Account(/* account details */));

        mockMvc.perform(MockMvcRequestBuilders.post("/bookings/create")
                        .param("userId", "1")
                        .param("routeId", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetBookingsByUserId() throws Exception {
        // Mock the service response
        List<ActiveBookings> activeBookings = Collections.singletonList(new ActiveBookings());
        when(activeBookingService.fetchActiveBookingsByUserId(1L)).thenReturn(activeBookings);

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/get/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteBookingById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
