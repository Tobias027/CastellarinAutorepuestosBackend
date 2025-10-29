package com.castellarin.autorepuestos.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddressDto {
    private String contactName;
    private String contactSurname;
    private String contactEmail;
    private String contectPhone;
    private String address;
    private String apartmentUnit;
    private String city;
    private String district;
    private String state;
    private String zip;
}
