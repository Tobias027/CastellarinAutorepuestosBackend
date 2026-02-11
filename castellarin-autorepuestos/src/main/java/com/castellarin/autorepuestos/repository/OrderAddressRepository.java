package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
