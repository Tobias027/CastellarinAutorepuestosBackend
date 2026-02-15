package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Payment findPaymentById(String id);
}
