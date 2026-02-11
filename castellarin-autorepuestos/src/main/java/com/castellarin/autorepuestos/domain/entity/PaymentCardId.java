package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PaymentCardId implements Serializable {
    private Long paymentId;
    private String CardId;
}
