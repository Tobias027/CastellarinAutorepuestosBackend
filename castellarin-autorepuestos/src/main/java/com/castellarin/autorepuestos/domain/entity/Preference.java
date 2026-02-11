package com.castellarin.autorepuestos.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {
    private Optional<User> user;
    private List<OrderItem> orderItems;
    //TODO
    private Double shippingCost = 0.0;
    private Long total;
    private Object shipments;
    private Map<String, Object> metadata;
    private Object backUrls;
    private String notificationUrl;
    private String statementDescription;
}