package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingAdressRepository extends JpaRepository<BillingAddress, Long> {
}
