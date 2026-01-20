package com.castellarin.autorepuestos.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCartDto {
    private String partNumber;
    private String name;
    private double price;
    private double offerPrice;
    private String imageUrl;
    private boolean isActive;
}
