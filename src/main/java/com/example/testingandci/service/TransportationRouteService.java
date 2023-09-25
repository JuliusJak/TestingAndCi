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
    public TransportationRoute updateRoute(TransportationRoute oldRoute) {
        return repository.save(oldRoute);
    }

    @Override
    public TransportationRoute fetchRouteById(Long routeId) {
        return repository.findById(routeId).orElse(null);
    }
    @Override
    public List<TransportationRoute> fetchAllRoutes() {
        return repository.findAll();
    }

    @Override
    public TransportationRoute createNewRoute(TransportationRoute newRoute) {
        return repository.save(newRoute);
    }
    @Override
    public void deleteRoute(Long routeId) {
        repository.deleteById(routeId);
    }
}