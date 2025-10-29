package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.ProductDto;
import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.Product;

public class ProductMapper {
    public static ProductDto toDto(Product product){
        return new ProductDto(
                product.getProductId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getOfferPrice(),
                product.getImageUrl(),
                product.getIsActive()
        );
    }
}
