package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.ProductVehicleComp;
import com.castellarin.autorepuestos.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductVehicleCompRepository extends JpaRepository<ProductVehicleComp, Long> {

    @Query(value =
            "SELECT v.* " +
            "FROM product_vehicle_comps pvc " +
            "LEFT JOIN vehicles v ON pvc.vehicle_id = v.vehicle_id " +
            "WHERE pvc.product_id = :productId",
            nativeQuery = true)
    List<Vehicle> findCompatible(long productId);
}
