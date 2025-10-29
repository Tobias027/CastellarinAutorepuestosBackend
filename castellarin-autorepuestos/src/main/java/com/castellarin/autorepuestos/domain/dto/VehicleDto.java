package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.VehicleBrand;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    private VehicleBrand Brand;
    private String Model;
    private int startProduction;
    private int endProduction;
    private String engine;
}
