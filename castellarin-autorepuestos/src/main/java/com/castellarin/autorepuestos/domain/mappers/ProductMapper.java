package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDto toDto(Product product){
        return new ProductDto(
                product.getPartNumber(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getOfferPrice(),
                product.getImagePath(),
                product.getIsActive()
        );
    }

    public static List<ProductDto> toDtos(List<Product> products){
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }
}
