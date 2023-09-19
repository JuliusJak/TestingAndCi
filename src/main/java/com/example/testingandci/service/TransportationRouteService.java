package com.example.testingandci.service;

import com.example.testingandci.model.TransportationRoute;
import com.example.testingandci.repository.ITransportationRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportationRouteService implements ITransportationRouteService {
    @Autowired
    ITransportationRouteRepository repository;

    @Override
    public TransportationRoute updateRoute(Long accountId) {
        return null;
    }

    @Override
    public List<TransportationRoute> fetchAllRoutes() {
        return null;
    }

    @Override
    public TransportationRoute createNewRoute(TransportationRoute newRoute) {
        return null;
    }
}