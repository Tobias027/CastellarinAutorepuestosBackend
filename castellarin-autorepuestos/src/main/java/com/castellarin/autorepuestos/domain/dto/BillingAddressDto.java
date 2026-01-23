package com.castellarin.autorepuestos.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@Data
@AllArgsConstructor
public class BillingAddressDto {
    private String contactName;
    private String contactLastname;
    private String street;
    private String addressNumber;
    private String apartmentUnit;
    private String city;
    private String district;
    private String state;
    private String zip;
}
