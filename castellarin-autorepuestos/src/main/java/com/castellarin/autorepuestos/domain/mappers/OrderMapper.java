package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.entity.Order;

public class OrderMapper {
    public static OrderDto toDto(Order order) {
        return new OrderDto(
                OrderItemMapper.toDtos(order.getItems()),
                OrderAddressMapper.toDto(order.getAddress()),
                BillingAddressMapper.toDto(order.getBillingAddress()),
                order.getNotes()
        );
    }
}
