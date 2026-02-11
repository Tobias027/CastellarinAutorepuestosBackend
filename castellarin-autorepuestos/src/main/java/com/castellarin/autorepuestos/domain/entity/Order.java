package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="orders")
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(
            name = "user_email",
            referencedColumnName = "email",
            nullable = true
    )
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST)
    private ShippingAddress address;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST)
    private BillingAddress billingAddress;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST)
    private Payment payment;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name="order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void addOrderItem(OrderItem item) {
        this.items.add(item);
        item.setOrder(this);
    }

    public void setAddress(ShippingAddress address) {
        this.address = address;
        address.setOrder(this);
    }


}
