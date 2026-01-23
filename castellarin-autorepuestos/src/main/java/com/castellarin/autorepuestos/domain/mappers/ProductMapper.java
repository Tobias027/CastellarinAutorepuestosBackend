package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.ProductCartDto;
import com.castellarin.autorepuestos.domain.dto.ProductDto;
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

    public static ProductCartDto toCartProductDto(Product product){
        return new ProductCartDto(
                product.getPartNumber(),
                product.getName(),
                product.getPrice(),
                product.getOfferPrice(),
                product.getImagePath(),
                product.getIsActive()
        );
    }

    public static List<ProductCartDto> toCartProductsDto(List<Product> products){
        return products.stream().map(ProductMapper::toCartProductDto).collect(Collectors.toList());
    }
}
