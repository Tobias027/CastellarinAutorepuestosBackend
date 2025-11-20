package com.castellarin.autorepuestos.domain.mappers;

import com.castellarin.autorepuestos.domain.dto.BillingAddressDto;
import com.castellarin.autorepuestos.domain.entity.BillingAddress;

import java.util.Optional;

public class BillingAddressMapper {
    public static BillingAddressDto toDto(BillingAddress billingAddress) {
        return new BillingAddressDto(
                billingAddress.getContactName(),
                billingAddress.getContactSurname(),
                billingAddress.getStreet(),
                billingAddress.getAddressNumber(),
                Optional.ofNullable(billingAddress.getApartmentUnit()),
                billingAddress.getCity(),
                billingAddress.getDistrict(),
                billingAddress.getState(),
                billingAddress.getZip()
        );
    }
}
