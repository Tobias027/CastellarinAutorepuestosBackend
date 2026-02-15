package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="product_vehicle_comps")
public class ProductVehicleComp {

    @EmbeddedId
    private ProductVehicleCompId id;

    @ManyToOne
    @MapsId("vehicleId")
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @MapsId("partNumber")
    @JoinColumn(name = "part_number")
    private Product product;
}
