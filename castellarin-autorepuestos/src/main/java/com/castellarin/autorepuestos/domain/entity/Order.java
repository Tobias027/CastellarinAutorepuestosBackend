package com.castellarin.autorepuestos.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "order_id", nullable = false)
    private String orderId;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<Payment> payment;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> items = new ArrayList<>();

    @Column(name="status", nullable = false)
    private String status;

    @Column(name="status_details", nullable = false)
    private String statusDetails;

    @Column(nullable = false)
    private Double tax;

    @Column(nullable = false)
    private Double shipping;

    @Column(nullable = false)
    private Double total;


    @Column()
    private String notes;

    @Column(name = "stock_reserved", nullable = false)
    private Boolean stockReserved;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
