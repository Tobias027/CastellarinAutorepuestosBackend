package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.ProductBrand;
import com.castellarin.autorepuestos.domain.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDto {
    private String name;
    private ProductBrand brand;
    private String partNumber;
    private Category category;
    private String description;
    private Map<String, Object> specs;
    private double price;
    private double offerPrice;
    private int stock;
    private String imagePath;
    private String notes;
    private List<VehicleDto> compatibleVehicles;
}
