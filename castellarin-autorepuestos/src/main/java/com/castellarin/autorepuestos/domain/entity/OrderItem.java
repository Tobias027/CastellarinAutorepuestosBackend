package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="order_items")
public class OrderItem {

    @EmbeddedId
    @Column(name = "order_item_id")
    private OrderItemId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name="order_id",  nullable = false)
    @ToString.Exclude
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id",nullable = false)
    private Product product;

    @Column(name="quantity", nullable = false)
    private Integer quantity;

    @Column(name="unit_price", nullable = false)
    private Double unitPrice;

    @Column(name="subtotal", nullable = false)
    private Double subtotal;
}
