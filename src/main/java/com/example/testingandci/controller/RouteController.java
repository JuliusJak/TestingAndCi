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
    public TransportationRoute createRoute() {
        TransportationRoute transportationRoute = TransportationRoute.builder()
                .arrivalPoint("stockholm")
                .departurePoint("malmo")
                .discountPrice(0)
                .estimatedArrival("11:15")
                .transportationCompany("SJ")
                .typeOfTransport("train")
                .estimatedDeparture("08:00")
                .ticketPrice(100)
                .build();
        return transportationRouteService.createNewRoute(transportationRoute);
    }
    @GetMapping("get")
    public ResponseEntity<List<TransportationRoute>> getAllRoutes() {
        List<TransportationRoute> routes = transportationRouteService.fetchAllRoutes();
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
