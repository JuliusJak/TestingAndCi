package com.example.testingandci.repository;

import com.example.testingandci.model.PaymentsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentHistoryRepository extends JpaRepository<PaymentsHistory, Long> {
}