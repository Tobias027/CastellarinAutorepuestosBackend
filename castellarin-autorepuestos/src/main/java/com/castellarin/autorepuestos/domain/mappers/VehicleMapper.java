package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.dto.VehicleDto;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.entity.Vehicle;
import com.castellarin.autorepuestos.domain.entity.VehicleBrand;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleMapper {
    public static VehicleDto toDto(Vehicle vehicle){
        return new VehicleDto(
            vehicle.getBrand().getBrand(),
            vehicle.getModel(),
            vehicle.getStartProduction(),
            vehicle.getEndProduction(),
            vehicle.getEngine()
        );
    }

    public static List<VehicleDto> toDtos(List<Vehicle> vehicles){
        return vehicles.stream().map(VehicleMapper::toDto).collect(Collectors.toList());
    }
}
