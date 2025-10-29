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
    @Column(name = "vehicle_brand", nullable = false)
    private String brand;
}
