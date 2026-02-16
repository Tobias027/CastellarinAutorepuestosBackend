package com.castellarin.autorepuestos.repository;

import com.castellarin.autorepuestos.domain.entity.ProductVehicleComp;
import com.castellarin.autorepuestos.domain.entity.ProductVehicleCompId;
import com.castellarin.autorepuestos.domain.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductVehicleCompRepository extends JpaRepository<ProductVehicleComp, ProductVehicleCompId> {

    @Query(value =
            "SELECT v.* " +
            "FROM product_vehicle_comps pvc " +
            "LEFT JOIN vehicles v ON pvc.vehicle_id = v.vehicle_id " +
            "WHERE pvc.part_number = :partNumber",
            nativeQuery = true)
    List<Vehicle> findByPartNumber(@Param("partNumber") String partNumber);
}
