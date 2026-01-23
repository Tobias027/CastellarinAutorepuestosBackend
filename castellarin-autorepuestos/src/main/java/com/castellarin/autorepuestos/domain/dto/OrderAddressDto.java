package com.castellarin.autorepuestos.domain.dto;

import com.castellarin.autorepuestos.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
public class OrderAddressDto {
    private String contactName;
    private String contactLastname;
    private String contactEmail;
    private String contactPhone;
    private String street;
    private String addressNumber;
    private String apartmentUnit;
    private String city;
    private String district;
    private String state;
    private String zip;
}
