package com.example.testingandci.repository;

import com.example.testingandci.model.TransportationRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRouteRepository extends JpaRepository<TransportationRoute, Long> {
    List<TransportationRoute> findByTransportationCompany(String transportationCompany);
}