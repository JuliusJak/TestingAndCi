package com.example.testingandci.unitTesting;

import com.example.testingandci.controller.RouteController;
import com.example.testingandci.model.TransportationRoute;
import com.example.testingandci.service.RouteService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteControllerUnitTests {
    @Mock
    private RouteService routeService;
    @InjectMocks
    private RouteController routeController;


    @ParameterizedTest
    @CsvSource({
            "Point A, Point B, 0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, USER",
            ", Point B, 0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, ADMIN",
            "Point A, Point B, -0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, PROVIDER",
            ",,,,,,,,,"
    })
    public void testCreateRoute(
            String arrivalPoint,
            String departurePoint,
            Double discountPrice,
            String estimatedArrival,
            String transportationCompany,
            String typeOfTransport,
            String estimatedDeparture,
            Integer ticketPrice,
            String accountType) {


        if (arrivalPoint == null
                || departurePoint == null
                || discountPrice == null
                || estimatedArrival == null
                || transportationCompany == null
                || typeOfTransport == null
                || estimatedDeparture == null
                || ticketPrice == null
                || accountType == null) {

            assertThrows(NullPointerException.class, () -> routeController.createRoute(
                    arrivalPoint, departurePoint, discountPrice, estimatedArrival,
                    transportationCompany, typeOfTransport, estimatedDeparture,
                    ticketPrice, accountType));
        } else if (discountPrice <= 0 || ticketPrice <= 0){
            assertThrows(IllegalArgumentException.class, () -> routeController.createRoute(
                    arrivalPoint, departurePoint, discountPrice, estimatedArrival, transportationCompany,
                    typeOfTransport, estimatedDeparture, ticketPrice, accountType));
        } else if (accountType.equals("USER")){
            assertThrows(IllegalArgumentException.class, () -> routeController.createRoute(
                    arrivalPoint, departurePoint, discountPrice, estimatedArrival, transportationCompany,
                    typeOfTransport, estimatedDeparture, ticketPrice, accountType));
        }

        else {
            TransportationRoute mockTransportationRoute = new TransportationRoute();
            when(routeService.createNewRoute(any(TransportationRoute.class))).thenReturn(mockTransportationRoute);

            TransportationRoute result = routeController.createRoute(
                    arrivalPoint,
                    departurePoint,
                    discountPrice,
                    estimatedArrival,
                    transportationCompany,
                    typeOfTransport,
                    estimatedDeparture,
                    ticketPrice,
                    accountType
            );

            verify(routeService, times(1)).createNewRoute(any(TransportationRoute.class));
            assertEquals(mockTransportationRoute, result);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "Point A, Point B",
            ", Point B",
            "Point A, Point B",
            ",,"
    })    public void testGetAllRoutes(
            String arrivalPoint,
            String departurePoint) {

        TransportationRoute transportationRoute = TransportationRoute.builder()
                .arrivalPoint(arrivalPoint)
                .departurePoint(departurePoint)
                .build();

        List<TransportationRoute> sampleRoutes = new ArrayList<>();
        sampleRoutes.add(transportationRoute);

        when(routeService.fetchAllRoutes()).thenReturn(sampleRoutes);

        ResponseEntity<List<TransportationRoute>> response = routeController.getAllRoutes();

        verify(routeService, times(1)).fetchAllRoutes();

        assertEquals(ResponseEntity.ok(sampleRoutes), response);

        assertEquals(sampleRoutes, response.getBody());

    }

    @ParameterizedTest
    @CsvSource({
            "Point A, Point B, 0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, USER",
            ", Point B, 0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, ADMIN",
            "Point A, Point B, -0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, PROVIDER",
            ",,,,,,,,,"
    })    public void testGetRoutesByTransportationCompany(
            String arrivalPoint,
            String departurePoint,
            Double discountPrice,
            String estimatedArrival,
            String transportationCompany,
            String typeOfTransport,
            String estimatedDeparture,
            Integer ticketPrice,
            String accountType) {

        TransportationRoute transportationRoute = TransportationRoute.builder()
                .arrivalPoint(arrivalPoint)
                .departurePoint(departurePoint)
                .transportationCompany(transportationCompany)
                .build();

        List<TransportationRoute> sampleRoutes = new ArrayList<>();
        sampleRoutes.add(transportationRoute);

        if (transportationCompany == null){
            assertThrows(NullPointerException.class, () -> routeController.createRoute(
                    arrivalPoint, departurePoint, discountPrice, estimatedArrival, transportationCompany,
                    typeOfTransport, estimatedDeparture, ticketPrice,accountType));
        } else {

            when(routeService.getRoutesByTransportationCompany(transportationCompany)).thenReturn(sampleRoutes);

            ResponseEntity<List<TransportationRoute>> response = routeController.getRoutesByTransportationCompany(transportationCompany);

            verify(routeService, times(1)).getRoutesByTransportationCompany(transportationCompany);

            assertEquals(ResponseEntity.ok(sampleRoutes), response);

            assertEquals(sampleRoutes, response.getBody());

        }

    }
    @ParameterizedTest
    @CsvSource({
            "1,Point A, Point B, 0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, USER",
            "2,, Point B, 0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, ADMIN",
            "3,Point A, Point B, -0.2, 2023-10-01, Company X, Bus, 2023-09-30, 100, PROVIDER",
            ",,,,,,,,,,"
    })    public void testGetRoutesById(
            Long routeId,
            String arrivalPoint,
            String departurePoint,
            Double discountPrice,
            String estimatedArrival,
            String transportationCompany,
            String typeOfTransport,
            String estimatedDeparture,
            Integer ticketPrice,
            String accountType) {

        TransportationRoute transportationRoute = TransportationRoute.builder()
                .arrivalPoint(arrivalPoint)
                .departurePoint(departurePoint)
                .estimatedArrival(estimatedArrival)
                .estimatedDeparture(estimatedDeparture)
                .transportationCompany(transportationCompany)
                .typeOfTransport(typeOfTransport)
                .estimatedDeparture(estimatedDeparture)
                .build();


        if (routeId == null){
            assertThrows(NullPointerException.class, () -> routeController.createRoute(
                    arrivalPoint, departurePoint, discountPrice, estimatedArrival, transportationCompany,
                    typeOfTransport, estimatedDeparture, ticketPrice, accountType));
        } else {

            when(routeService.fetchRouteById(routeId)).thenReturn(transportationRoute);

            ResponseEntity<TransportationRoute> response = routeController.getRouteById(routeId);

            verify(routeService, times(1)).fetchRouteById(routeId);

            assertEquals(ResponseEntity.ok(transportationRoute), response);

            assertEquals(transportationRoute, response.getBody());

        }

    }

    @ParameterizedTest
    @CsvSource({"1","2",","})
    public void testDeleteRoute(Long userId) {

        if (userId == null){
            assertThrows(NullPointerException.class, () -> routeController.deleteRoute(userId));
        } else if (userId < 0){
            assertThrows(IllegalArgumentException.class, () -> routeController.deleteRoute(userId));
        }
            else {
            routeController.deleteRoute(userId);
            verify(routeService, times(1)).deleteRoute(userId);
        }
    }
}
