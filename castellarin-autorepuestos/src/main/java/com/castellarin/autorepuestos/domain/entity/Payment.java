package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "payments")
public class Payment {

    @Id
    @Column(name = "payment_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(mappedBy = "payment",cascade = CascadeType.PERSIST, orphanRemoval = false)
    private PayerDetails payerDetails;

    @OneToMany(mappedBy = "payment",cascade = CascadeType.PERSIST)
    private List<PaymentCard> paymentCards;

    @Column(name = "payment_method_id", nullable = false)
    private String paymentMethodId;

    @Column(name = "payment_type_id", nullable = false)
    private String paymentTypeId;

    @Column(name = "binary_mode", nullable = false)
    private String binaryMode;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "status_detail", nullable = false)
    private String statusDetail;

    @Column(name = "taxes_amount", nullable = false)
    private Double taxesAmount;

    @Column(name = "shipping_amount", nullable = false)
    private Double shippingAmount;

    @Column(name = "net_received_amount", nullable = false)
    private Double netReceivedAmount;

    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;
}
