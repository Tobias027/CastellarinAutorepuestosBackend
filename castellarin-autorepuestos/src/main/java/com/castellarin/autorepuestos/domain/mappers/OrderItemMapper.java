package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.OrderItemDto;
import com.castellarin.autorepuestos.domain.entity.Order;
import com.castellarin.autorepuestos.domain.entity.OrderItem;
import com.castellarin.autorepuestos.domain.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapper {
    public static OrderItemDto toDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProduct().getPartNumber(),
                orderItem.getQuantity()
        );
    }
    public static List<OrderItemDto> toDtos(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItemMapper::toDto).collect(Collectors.toList());
    }
}
