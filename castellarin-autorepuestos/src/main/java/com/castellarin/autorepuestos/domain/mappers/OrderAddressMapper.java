package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.OrderAddressDto;
import com.castellarin.autorepuestos.domain.entity.AddressType;
import com.castellarin.autorepuestos.domain.entity.OrderAddress;

import java.util.Optional;

public class OrderAddressMapper {

    public static OrderAddress ToEntity(OrderAddressDto orderAddressDto) {
        return new OrderAddress(
                null,
                null,
                orderAddressDto.getContactName(),
                orderAddressDto.getContactEmail(),
                orderAddressDto.getContectPhone(),
                orderAddressDto.getStreet(),
                orderAddressDto.getAddressNumber(),
                orderAddressDto.getApartmentUnit().orElse(null),
                orderAddressDto.getCity(),
                orderAddressDto.getDistrict(),
                orderAddressDto.getState(),
                orderAddressDto.getZip()
        );
    }

    public static OrderAddressDto toDto(OrderAddress orderAddress) {
        return new OrderAddressDto(
                orderAddress.getContactName(),
                orderAddress.getContactName(),
                orderAddress.getContactEmail(),
                orderAddress.getContectPhone(),
                orderAddress.getStreet(),
                orderAddress.getAddressNumber(),
                Optional.ofNullable(orderAddress.getApartmentUnit()),
                orderAddress.getCity(),
                orderAddress.getDistrict(),
                orderAddress.getState(),
                orderAddress.getZip()
        );
    }


    private static AddressType getAddressType(String address) {
        return switch (address) {
            case "Shipping" -> AddressType.SHIPPING;
            case "Billing" -> AddressType.BILLING;
            default -> AddressType.BOTH;
        };
    }
}
