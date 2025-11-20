package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicle_brands")
public class VehicleBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_brand_id")
    private Long id;

    @Column(name = "vehicle_brand", nullable = false, unique = true)
    private String brand;
}
