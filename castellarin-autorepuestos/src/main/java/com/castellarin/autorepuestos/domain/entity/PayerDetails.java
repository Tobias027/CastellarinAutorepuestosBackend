package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "payers_details")
public class PayerDetails {

    @Id
    @OneToOne
    @JoinColumn(name="payment_id",nullable = false)
    private Payment payment;

    @Column(name = "payer_id",nullable = false)
    private String payerId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String email;

    @Column(name = "identification_type",nullable = false)
    private String identificationType;

    @Column(name = "identification_number",nullable = false)
    private String identificationNumber;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "phone_extension",nullable = false)
    private String phoneExtension;

    @Column(name = "phone_area_code",nullable = false)
    private String phoneAreaCode;
}
