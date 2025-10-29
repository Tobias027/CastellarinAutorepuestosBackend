package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemId implements Serializable {
    private Long orderId;
    private Long productId;
}
