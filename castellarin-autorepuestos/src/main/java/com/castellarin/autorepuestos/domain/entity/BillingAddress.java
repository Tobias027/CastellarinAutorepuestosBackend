package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="billing_address")
public class BillingAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "billing_address_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "contact_name", nullable = false)
    private String contactName;

    @Column(name = "contact_surname", nullable = false)
    private String contactSurname;

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
    private String state;

    @Column(nullable = false)
    private String zip;
}
