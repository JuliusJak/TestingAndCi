package com.example.testingandci.controller;

import com.example.testingandci.model.TransportationRoute;
import com.example.testingandci.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequestMapping("route/")
public class RouteController {
    @Autowired
    private RouteService routeService;

    //TODO USERS can not create routes only ADMINS and PROVIDERS
    @PostMapping("create")
    public TransportationRoute createRoute(
            @RequestParam("arrivalPoint") String arrivalPoint,
            @RequestParam("departurePoint") String departurePoint,
            @RequestParam("discountPrice") Double discountPrice,
            @RequestParam("estimatedArrival") String estimatedArrival,
            @RequestParam("transportationCompany") String transportationCompany,
            @RequestParam("typeOfTransport") String typeOfTransport,
            @RequestParam("estimatedDeparture") String estimatedDeparture,
            @RequestParam("ticketPrice") Integer ticketPrice) {

        if (arrivalPoint == null
                || departurePoint == null
                || discountPrice == null
                || estimatedArrival == null
                || transportationCompany == null
                || typeOfTransport == null
                || estimatedDeparture == null
                || ticketPrice == null) {

            throw new NullPointerException("Parameters can not be null");
        }
        else if (arrivalPoint.isEmpty()
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
        return routeService.createNewRoute(transportationRoute);
    }
    @GetMapping("get/all")
    public ResponseEntity<List<TransportationRoute>> getAllRoutes() {
        List<TransportationRoute> routes = routeService.fetchAllRoutes();
        return ResponseEntity.ok(routes);
    }
    @GetMapping("get/supplier")
    public ResponseEntity<List<TransportationRoute>> getRoutesByTransportationCompany(
            @RequestParam("transportationCompany") String transportationCompany) {
        List<TransportationRoute> routes = routeService.getRoutesByTransportationCompany(transportationCompany);
        if (transportationCompany == null || transportationCompany.isEmpty()){
            throw new NullPointerException("transportationCompany can not be empty or null");
        } else {
            return ResponseEntity.ok(routes);
        }
    }
    @GetMapping("get/id")
    public ResponseEntity<TransportationRoute> getRouteById(@RequestParam Long id) {
        TransportationRoute transportationRoute = routeService.fetchRouteById(id);
        if (id == null) {
            throw new NullPointerException("parameter id can not be null");
        } else {
            return ResponseEntity.ok(transportationRoute);
        }
    }
    // TODO get routes by destination and starting point
    @DeleteMapping("delete")
    public void deleteRoute(@RequestParam Long id) {

        if (id == null){
            throw new NullPointerException("parameter id can not be null");
        } else {
            routeService.deleteRoute(id);
        }
    }
    // TODO PROVIDERS can update their campaigns BUT only their own

}
