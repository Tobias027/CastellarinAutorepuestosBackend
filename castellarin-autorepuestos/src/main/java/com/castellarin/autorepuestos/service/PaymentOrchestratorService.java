package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PaymentOrchestratorService {

    private final OrderOrchestratorService orderOrchestratorService;
    private final ProductsService productsService;
    private final PaymentRepository paymentRepository;
    private final PayerDetailsRepository payerDetailsRepository;
    private final PaymentCardRepository paymentCardRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final BillingAddressRepository billingAddressRepository;

    public Payment createPayment(Map<String,Object> responseBody) {

        Order order = orderOrchestratorService.getOrderById((String) responseBody.get("order.id"));

        //shipping address
        ShippingAddress shippingAddress = ShippingAddress.builder()
                .order(order)
                .contactName((String) responseBody.get("metadata.shipping_address.contact_name"))
                .contactLastname((String) responseBody.get("metadata.shipping_address.contact_lastname"))
                .contactEmail((String) responseBody.get("metadata.shipping_address.contact_email"))
                .contectPhone((String) responseBody.get("metadata.shipping_address.contact_phone"))
                .street((String) responseBody.get("metadata.shipping_address.street"))
                .city((String) responseBody.get("metadata.shipping_address.city"))
                .district((String) responseBody.get("metadata.shipping_address.district"))
                .zip((String) responseBody.get("metadata.shipping_address.zip"))
                .province((String) responseBody.get("metadata.shipping_address.province"))
                .apartmentUnit((String) responseBody.get("metadata.shipping_address.apartment_unit"))
                .build();

        shippingAddressRepository.save(shippingAddress);

        //billing address
        BillingAddress billingAddress = BillingAddress.builder()
                .order(order)
                .contactName((String) responseBody.get("metadata.billing_address.contact_name"))
                .contactLastname((String) responseBody.get("metadata.billing_address.contact_lastname"))
                .street((String) responseBody.get("metadata.billing_address.street"))
                .city((String) responseBody.get("metadata.billing_address.city"))
                .district((String) responseBody.get("metadata.billing_address.district"))
                .zip((String) responseBody.get("metadata.billing_address.zip"))
                .apartmentUnit((String) responseBody.get("metadata.billing_address.apartment_unit"))
                .build();

        billingAddressRepository.save(billingAddress);

        //payment
        Payment payment = Payment.builder()
                .id((String) responseBody.get("id"))
                .order(order)
                .paymentMethodId((String) responseBody.get("payment_method_id"))
                .paymentTypeId((String) responseBody.get("payment_type_id"))
                .status((String) responseBody.get("status"))
                .statusDetail((String) responseBody.get("status_detail"))
                .taxesAmount(Double.parseDouble(responseBody.get("taxes_amount").toString()))
                .transactionAmount(Double.parseDouble(responseBody.get("transaction_amount").toString()))
                .netReceivedAmount(Double.parseDouble(responseBody.get("transaction_details.net_received_amount").toString()))
                .build();

        final Payment savedPayment =paymentRepository.save(payment);

        //payerDetails
        PayerDetails payerDetails = PayerDetails.builder()
                .payerId((String) responseBody.get("payer.id"))
                .payment(savedPayment)
                .entityType((String) responseBody.get("payer.entity_type"))
                .email((String) responseBody.get("payer.email"))
                .firstName((String) responseBody.get("payer.first_name"))
                .lastName((String) responseBody.get("payer.last_name"))
                .identificationType((String) responseBody.get("payer.identification.type"))
                .identificationNumber((String) responseBody.get("payer.identification.number"))
                .phoneAreaCode((String) responseBody.get("payer.phone.extension"))
                .phoneExtension((String) responseBody.get("payer.phone.area_code"))
                .phoneNumber((String) responseBody.get("payer.phone.number"))
                .build();

        payerDetailsRepository.save(payerDetails);

        //paymentCard
        PaymentCard paymentCard = PaymentCard.builder()
                .cardId((String) responseBody.get("card.id"))
                .payment(savedPayment)
                .bin((String) responseBody.get("card.bin"))
                .cardholderIdentificationType((String) responseBody.get("card.identification.type"))
                .cardholderIdentificationNumber((String) responseBody.get("card.identification.number"))
                .country((String) responseBody.get("card.country"))
                .expirationYear((String) responseBody.get("card.expiration_year"))
                .expirationMonth((String) responseBody.get("card.expiration_month"))
                .firstSixDigits((String) responseBody.get("card.first_six_digits"))
                .lastFourDigits((String) responseBody.get("card.last_four_digits"))
                .build();

        paymentCardRepository.save(paymentCard);

        syncStock(order,(String) responseBody.get("status"));

        return savedPayment;
    }

    //update Payment
    public Payment updatePayment(Map<String, Object> responseBody){
        Payment payment =  paymentRepository.findPaymentById((String) responseBody.get("id"));

        String newStatus = (String) responseBody.get("status");
        String newStatusDetail = (String) responseBody.get("status_detail");

        if(!payment.getStatus().equals(newStatus)){
            payment.setStatus(newStatus);
            payment.setStatusDetail(newStatusDetail);
        }

        Order order = orderOrchestratorService.getOrderById((String) responseBody.get("order.id"));

        syncStock(order,(String) newStatus);

        return paymentRepository.save(payment);
    }

    public void syncStock(Order order, String newStatus) {

        List<String> activeStates = Arrays.asList("pending", "approved", "in_process", "authorized", "in_mediation", "charged_back");
        List<String> inactiveStates = Arrays.asList("cancelled", "refunded", "rejected");


        if(activeStates.contains(newStatus) && !order.getStockReserved()){
            productsService.incrementProductStock(order.getItems());
            order.setStockReserved(true);
            System.out.println("SE INCREMENTO EL STOCK");
        } else if (inactiveStates.contains(newStatus) && order.getStockReserved()) {
            productsService.decrementProductStock(order.getItems());
            order.setStockReserved(false);
            System.out.println("SE DECREMENTO EL STOCK");
        }
    }
}
