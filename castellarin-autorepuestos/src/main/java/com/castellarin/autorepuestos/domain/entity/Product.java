package com.castellarin.autorepuestos.domain.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_brand", nullable = false)
    private ProductBrand brand;

    @Column(name="part_number")
    private String partNumber;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> specs;

    @Column(nullable = false)
    private Double price;

    @Column(name="offer_price", nullable = false)
    private Double offerPrice;

    @Column(nullable = false)
    private Integer stock;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @Column(name="is_active", nullable = false)
    private Boolean isActive;

    @Column(name="cost_price")
    private Double costPrice;

    @Column()
    private String notes;
}
