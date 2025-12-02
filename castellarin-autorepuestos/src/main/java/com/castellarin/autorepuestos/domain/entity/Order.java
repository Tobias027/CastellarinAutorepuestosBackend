package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(
            name = "user_email",
            referencedColumnName = "email",
            nullable = false
    )
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private OrderAddress address;

    @OneToOne(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name="order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double tax;

    @Column(nullable = false)
    private Double shipping;

    @Column(nullable = false)
    private Double total;

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

    public void setAddress(OrderAddress address) {
        this.address = address;
        address.setOrder(this);
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
        updatedAt = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now(ZoneId.of("America/Argentina/Buenos_Aires"));
    }

}
