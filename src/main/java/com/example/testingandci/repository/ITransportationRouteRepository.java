package com.example.testingandci.repository;

import com.example.testingandci.model.TransportationRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransportationRouteRepository extends JpaRepository<TransportationRoute, Long> {
}