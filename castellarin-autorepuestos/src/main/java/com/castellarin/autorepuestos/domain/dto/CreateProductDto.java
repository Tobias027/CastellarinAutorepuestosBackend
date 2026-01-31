package com.castellarin.autorepuestos.domain.dto;

import lombok.Data;

import java.util.Map;
@Data
public class CreateProductDto {
    private String name;
    private String brand;
    private String partNumber;
    private String category;
    private String description;
    private Map<String, Object> specs;
    private Double price;
    private Double offerPrice;
    private Integer stock;
    private Boolean isActive;
    private Double costPrice;
    private String notes;
}

