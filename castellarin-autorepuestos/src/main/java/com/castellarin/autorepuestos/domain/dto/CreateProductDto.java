package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.Category;
import com.castellarin.autorepuestos.domain.entity.ProductBrand;
import lombok.Data;

import java.util.Base64;
import java.util.Map;
@Data
public class CreateProductDto {
    //private Long productId;
    private String name;
    private String brand;
    private String partNumber;
    private String category;
    private String description;
    private Map<String, Object> specs;
    private Double price;
    private Double offerPrice;
    private Integer stock;
    private String base64Image;
    private Boolean isActive;
    private Double costPrice;
    private String notes;
}

