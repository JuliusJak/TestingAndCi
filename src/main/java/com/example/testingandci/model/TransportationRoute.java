package com.example.testingandci.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportationRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long routeId;
    private String departurePoint;
    private String arrivalPoint;
    private String typeOfTransport;
    private String estimatedDeparture;
    private String estimatedArrival;
    private int ticketPrice;
    private String transportationCompany;
    private double discountPrice;
}
// TODO maybe add a limited amount of tickets/bookings that can be sold/created per route
// TODO create some dummy routes by different PROVIDERS