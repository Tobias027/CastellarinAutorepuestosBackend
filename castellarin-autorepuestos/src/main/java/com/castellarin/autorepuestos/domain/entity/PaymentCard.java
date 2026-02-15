package com.castellarin.autorepuestos.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
@Table(name = "payment_cards")
public class PaymentCard {

    @EmbeddedId
    private PaymentCardId paymentCardId;

    @MapsId
    @ManyToOne
    @JoinColumn(name = "paymentId")
    private Payment payment;

    @MapsId
    @Column(name = "card_id")
    private String cardId;


    @Column(nullable = false)
    private String bin;

    @Column(name = "cardholder_identification_number", nullable = false)
    private String cardholderIdentificationNumber;

    @Column(name = "cardholder_identification_type", nullable = false)
    private String cardholderIdentificationType;

    @Column(nullable = false)
    private String country;

    @Column(name = "expiration_month", nullable = false)
    private String expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private String expirationYear;

    @Column(name = "first_six_digits", nullable = false)
    private String firstSixDigits;

    @Column(name = "last_four_digits", nullable = false)
    private String lastFourDigits;

}
