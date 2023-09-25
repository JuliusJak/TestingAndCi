package com.example.testingandci.controller;

import com.example.testingandci.model.ActiveBookings;
import com.example.testingandci.model.TransportationRoute;
import com.example.testingandci.service.TransportationRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("route/")
public class RouteController {
    @Autowired
    private TransportationRouteService transportationRouteService;

    @PostMapping("create")
    public TransportationRoute createRoute(
            @RequestParam("arrivalPoint") String arrivalPoint,
            @RequestParam("departurePoint") String departurePoint,
            @RequestParam("discountPrice") double discountPrice,
            @RequestParam("estimatedArrival") String estimatedArrival,
            @RequestParam("transportationCompany") String transportationCompany,
            @RequestParam("typeOfTransport") String typeOfTransport,
            @RequestParam("estimatedDeparture") String estimatedDeparture,
            @RequestParam("ticketPrice") int ticketPrice) {

        if (arrivalPoint.isEmpty()
                || departurePoint.isEmpty()
                || estimatedArrival.isEmpty()
                || transportationCompany.isEmpty()
                || typeOfTransport.isEmpty()
                || estimatedDeparture.isEmpty()) {
            throw new IllegalArgumentException("String parameters must not be empty");
        }

        if (discountPrice <= 0 || ticketPrice <= 0) {
            throw new IllegalArgumentException("Discount price and ticket price can not be a negative number or 0");
        }
        int finalTicketPrice = (int) (ticketPrice * discountPrice);

        TransportationRoute transportationRoute = TransportationRoute.builder()
                .arrivalPoint(arrivalPoint)
                .departurePoint(departurePoint)
                .discountPrice(discountPrice)
                .estimatedArrival(estimatedArrival)
                .transportationCompany(transportationCompany)
                .typeOfTransport(typeOfTransport)
                .estimatedDeparture(estimatedDeparture)
                .ticketPrice(finalTicketPrice)
                .build();
        return transportationRouteService.createNewRoute(transportationRoute);
    }
    @GetMapping("get")
    public ResponseEntity<List<TransportationRoute>> getAllRoutes() {
        List<TransportationRoute> routes = transportationRouteService.fetchAllRoutes();
        return ResponseEntity.ok(routes);
    }
    @GetMapping("get/supplier")
    public ResponseEntity<List<TransportationRoute>> getRoutesByTransportationCompany(
            @RequestParam("transportationCompany") String transportationCompany) {
        List<TransportationRoute> routes = transportationRouteService.getRoutesByTransportationCompany(transportationCompany);
        return ResponseEntity.ok(routes);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<TransportationRoute> getAccountById(@PathVariable long id) {
        TransportationRoute transportationRoute = transportationRouteService.fetchRouteById(id);
        if (transportationRoute != null) {
            return ResponseEntity.ok(transportationRoute);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("delete/{id}")
    public void deleteAccount(@PathVariable long id) {
        transportationRouteService.deleteRoute(id);
    }

}
