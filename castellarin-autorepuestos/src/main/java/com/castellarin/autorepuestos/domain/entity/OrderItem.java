package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="order_items")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="order_id",  nullable = false)
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    @Column(name="quantity", nullable = false)
    private Integer quantity;

    @Column(name="subtotal", nullable = false)
    private Double subtotal;
}
