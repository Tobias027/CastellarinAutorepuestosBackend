package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.ProductDetailsDto;
import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.dto.VehicleDto;
import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.Product;
import com.castellarin.autorepuestos.domain.entity.ProductBrand;
import com.castellarin.autorepuestos.domain.entity.Vehicle;

import java.util.List;
import java.util.Map;

public class ProductDetailsMapper {
    public static ProductDetailsDto toDto(Product product, List<Vehicle> compatibleVehicles) {
        return new ProductDetailsDto(
                product.getProductId(),
                product.getName(),
                product.getBrand(),
                product.getPartNumber(),
                product.getCategory(),
                product.getDescription(),
                product.getSpecs(),
                product.getPrice(),
                product.getOfferPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getNotes(),
                VehicleMapper.vehiclestoDto(compatibleVehicles)
        );
    }
}
