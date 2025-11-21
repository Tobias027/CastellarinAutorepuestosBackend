package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.BillingAddressDto;
import com.castellarin.autorepuestos.domain.entity.BillingAddress;

import java.util.Optional;

public class BillingAddressMapper {
    public static BillingAddressDto toDto(BillingAddress billingAddress) {
        return new BillingAddressDto(
                billingAddress.getContactName(),
                billingAddress.getContactLastname(),
                billingAddress.getStreet(),
                billingAddress.getAddressNumber(),
                Optional.ofNullable(billingAddress.getApartmentUnit()),
                billingAddress.getCity(),
                billingAddress.getDistrict(),
                billingAddress.getState(),
                billingAddress.getZip()
        );
    }

    public static BillingAddress toEntity(BillingAddressDto billingAddressDto) {
        return new BillingAddress(
                null,
                null,
                billingAddressDto.getContactName(),
                billingAddressDto.getContactLastname(),
                billingAddressDto.getStreet(),
                billingAddressDto.getAddressNumber(),
                billingAddressDto.getApartmentUnit().orElse(null),
                billingAddressDto.getCity(),
                billingAddressDto.getDistrict(),
                billingAddressDto.getState(),
                billingAddressDto.getZip()
        );
    }
}
