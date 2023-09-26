package com.example.testingandci.repository;

import com.example.testingandci.model.ActiveBookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActiveBookingsRepository extends JpaRepository<ActiveBookings, Long> {
    List<ActiveBookings> findAllByUserId(long userId);

}