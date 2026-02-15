package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name="shipping_addresses")
public class ShippingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_address_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    @ToString.Exclude
    private Order order;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_lastname", nullable = false)
    private String contactLastname;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @Column(name = "contect_phone", nullable = false)
    private String contectPhone;

    @Column(nullable = false)
    private String street;

    @Column(name = "address_number", nullable = false)
    private String addressNumber;

    @Column(name = "apartment_unit")
    private String apartmentUnit;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String zip;
}
