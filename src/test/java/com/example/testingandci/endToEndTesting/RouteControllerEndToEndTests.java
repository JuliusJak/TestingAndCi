package com.example.testingandci.endToEndTesting;

import com.example.testingandci.model.TransportationRoute;
import com.example.testingandci.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RouteControllerEndToEndTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RouteService routeService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testCreateRoute() throws Exception {
        String arrivalPoint = "Destination A";
        String departurePoint = "Origin B";
        Double discountPrice = 0.2;
        String estimatedArrival = "2023-10-31";
        String transportationCompany = "TransportCo";
        String typeOfTransport = "Bus";
        String estimatedDeparture = "2023-10-30";
        Integer ticketPrice = 50;
        String accountType = "ADMIN";

        mockMvc.perform(MockMvcRequestBuilders.post("/route/create")
                        .param("arrivalPoint", arrivalPoint)
                        .param("departurePoint", departurePoint)
                        .param("discountPrice", discountPrice.toString())
                        .param("estimatedArrival", estimatedArrival)
                        .param("transportationCompany", transportationCompany)
                        .param("typeOfTransport", typeOfTransport)
                        .param("estimatedDeparture", estimatedDeparture)
                        .param("ticketPrice", ticketPrice.toString())
                        .param("accountType", accountType)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testGetRoutesByTransportationCompany() throws Exception {
        List<TransportationRoute> routesForCompanyX = Arrays.asList(
                new TransportationRoute(),
                new TransportationRoute()
        );

        String transportationCompany = "CompanyX";

        when(routeService.getRoutesByTransportationCompany(transportationCompany)).thenReturn(routesForCompanyX);

        mockMvc.perform(MockMvcRequestBuilders.get("/route/get/supplier")
                        .param("transportationCompany", transportationCompany)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(routesForCompanyX.size()))
                .andExpect(jsonPath("$[0].arrivalPoint").value(routesForCompanyX.get(0).getArrivalPoint()))
                .andExpect(jsonPath("$[0].departurePoint").value(routesForCompanyX.get(0).getDeparturePoint()))
                .andExpect(jsonPath("$[1].arrivalPoint").value(routesForCompanyX.get(1).getArrivalPoint()))
                .andExpect(jsonPath("$[1].departurePoint").value(routesForCompanyX.get(1).getDeparturePoint()));
    }

    @Test
    public void testUpdateRoute() throws Exception {
        // Define a sample route and user for the update
        Long routeId = 1L;
        String arrivalPoint = "New Destination";
        String departurePoint = "New Origin";
        Double discountPrice = 0.3;
        String estimatedArrival = "2023-11-30";
        String transportationCompany = "TransportCo";
        String typeOfTransport = "Train";
        String estimatedDeparture = "2023-11-29";
        Integer ticketPrice = 60;
        String accountName = "TransportCo";

        // Mock the routeService to return the existing route
        TransportationRoute existingRoute = new TransportationRoute(/* existing route details */);
        when(routeService.fetchRouteById(routeId)).thenReturn(existingRoute);

        mockMvc.perform(MockMvcRequestBuilders.patch("/route/update")
                        .param("routeId", routeId.toString())
                        .param("arrivalPoint", arrivalPoint)
                        .param("departurePoint", departurePoint)
                        .param("discountPrice", discountPrice.toString())
                        .param("estimatedArrival", estimatedArrival)
                        .param("transportationCompany", transportationCompany)
                        .param("typeOfTransport", typeOfTransport)
                        .param("estimatedDeparture", estimatedDeparture)
                        .param("ticketPrice", ticketPrice.toString())
                        .param("accountName", accountName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}

