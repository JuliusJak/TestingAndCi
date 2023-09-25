package com.example.testingandci.service;

import com.example.testingandci.model.TransportationRoute;

import java.util.List;

public interface ITransportationRouteService {
    /*Update eventual discount*/
    TransportationRoute updateRoute(TransportationRoute oldRoute);

    List<TransportationRoute> fetchAllRoutes();
    List<TransportationRoute> getRoutesByTransportationCompany(String transportationCompany);

    TransportationRoute fetchRouteById(Long routeId);

    /*Create a new route*/
    TransportationRoute createNewRoute(TransportationRoute newRoute);

    void deleteRoute(Long routeId);
}