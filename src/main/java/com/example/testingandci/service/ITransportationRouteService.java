package com.example.testingandci.service;

import com.example.testingandci.model.TransportationRoute;

import java.util.List;

public interface ITransportationRouteService {
    /*Update eventual discount*/
    TransportationRoute updateRoute(Long accountId);

    /*Fetch a list of routes from all the contractors*/
    List<TransportationRoute> fetchAllRoutes();

    /*Create a new route*/
    TransportationRoute createNewRoute(TransportationRoute newRoute);

}