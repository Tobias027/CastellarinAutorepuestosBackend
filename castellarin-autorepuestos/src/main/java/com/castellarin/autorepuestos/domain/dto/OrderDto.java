package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.BillingAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
     private String email;
     private List<OrderItemDto> orderItemDtoList;
     private OrderAddressDto orderAddressDto;
     private BillingAddressDto billingAddressDto;
     private String notes;
}
