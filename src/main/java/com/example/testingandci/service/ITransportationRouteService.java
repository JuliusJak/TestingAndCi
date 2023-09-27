package com.example.testingandci.service;

import com.example.testingandci.model.TransportationRoute;

import java.util.List;

public interface ITransportationRouteService {
    TransportationRoute updateRoute(TransportationRoute oldRoute);

    List<TransportationRoute> fetchAllRoutes();
    List<TransportationRoute> getRoutesByTransportationCompany(String transportationCompany);

    TransportationRoute fetchRouteById(Long routeId);

    TransportationRoute createNewRoute(TransportationRoute newRoute);

    void deleteRoute(Long routeId);
}