package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.OrderAddressDto;
import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.OrderItemDto;
import com.castellarin.autorepuestos.domain.entity.BillingAddress;
import com.castellarin.autorepuestos.domain.entity.Order;
import com.castellarin.autorepuestos.domain.entity.OrderItem;
import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDto toDto(Order order) {
        return new OrderDto(
                order.getUser().getEmail(),
                OrderItemMapper.toDtos(order.getItems()),
                OrderAddressMapper.toDto(order.getAddress()),
                BillingAddressMapper.toDto(order.getBillingAddress()),
                order.getNotes()
        );
    }
}
