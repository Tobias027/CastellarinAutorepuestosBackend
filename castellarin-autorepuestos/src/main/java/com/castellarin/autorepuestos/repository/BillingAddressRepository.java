package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Repository;

@Repository
public interface BillingAddressRepository extends JpaRepository<BillingAddress, Long> {
}
