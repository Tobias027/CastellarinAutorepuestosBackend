package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String partNumber;
    private String name;
    private Category category;
    private double price;
    private double offerPrice;
    private String imageUrl;
    private boolean isActive;
}
