package com.castellarin.autorepuestos.domain.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", nullable = false, updatable = false, unique = true)
    private Long vehicleID;

    @ManyToOne
    @JoinColumn(
            name="vehicle_brand",
            referencedColumnName = "vehicle_brand",
            nullable=false
    )
    private VehicleBrand Brand;

    @Column(name = "model", nullable = false)
    private String Model;

    @Column(name = "start_production", nullable = false)
    private Integer startProduction;

    @Column(name = "end_production")
    private Integer endProduction;

    @Column(name = "engine", nullable = false)
    private String engine;

}
