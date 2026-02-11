package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.OrderAddressDto;
import com.castellarin.autorepuestos.domain.entity.ShippingAddress;


public class OrderAddressMapper {

    public static ShippingAddress ToEntity(OrderAddressDto orderAddressDto) {
        return new ShippingAddress(
                null,
                null,
                orderAddressDto.getContactName(),
                orderAddressDto.getContactEmail(),
                orderAddressDto.getContactPhone(),
                orderAddressDto.getStreet(),
                orderAddressDto.getAddressNumber(),
                orderAddressDto.getApartmentUnit(),
                orderAddressDto.getCity(),
                orderAddressDto.getDistrict(),
                orderAddressDto.getState(),
                orderAddressDto.getZip()
        );
    }

    public static OrderAddressDto toDto(ShippingAddress shippingAddress) {
        return new OrderAddressDto(
                shippingAddress.getContactName(),
                shippingAddress.getContactName(),
                shippingAddress.getContactEmail(),
                shippingAddress.getContectPhone(),
                shippingAddress.getStreet(),
                shippingAddress.getAddressNumber(),
                shippingAddress.getApartmentUnit(),
                shippingAddress.getCity(),
                shippingAddress.getDistrict(),
                shippingAddress.getState(),
                shippingAddress.getZip()
        );
    }
}
