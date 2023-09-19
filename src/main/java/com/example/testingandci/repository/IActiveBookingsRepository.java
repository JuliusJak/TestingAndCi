package com.example.testingandci.repository;

import com.example.testingandci.model.ActiveBookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActiveBookingsRepository extends JpaRepository<ActiveBookings, Long> {
}