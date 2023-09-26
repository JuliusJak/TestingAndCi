package com.example.testingandci.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveBookings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookingId;
    private  long routeId;
    private  long userId;

}