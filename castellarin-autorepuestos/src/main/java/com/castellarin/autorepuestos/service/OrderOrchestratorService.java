package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.domain.mappers.BillingAddressMapper;
import com.castellarin.autorepuestos.domain.mappers.OrderAddressMapper;
import com.castellarin.autorepuestos.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderOrchestratorService {

    private final ProductRepository productRepository;
    private final ProductRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final BillingAdressRepository billingAdressRepository;
    private final OrderItemsRepository orderItemsRepository;

    private final ProductsService productsService;
    private final UserService userService;

    @Transactional
    public Boolean createOrder(Map<String, Object> response) {

        //ORDER
        /*
        Order order = Order.builder()
                .notes((String) response.get("metadata.notes"))
                .status(OrderStatus.PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        //ORDER ITEMS
        List<Map<String, Object>> itemsBody = (List<Map<String, Object>>) response.get("additional_info.items");
        List<OrderItem> orderItems = itemsBody.stream()
                .map(item ->
                        {
                            return OrderItem.builder()
                                    .order(savedOrder)
                                    .product(productsService.getProductByProductPart((String) item.get("id")))
                                    .quantity(Integer.parseInt(item.get("quantity").toString()))
                                    .unitPrice(Integer.parseInt(item.get("unit_price").toString()))
                                    .subtotal(Integer.parseInt(item.get("unit_price").toString()) * Integer.parseInt(item.get("quantity").toString()))
                                    .build();
                        }
                )
                .collect(Collectors.toList());

        List<OrderItem> savedOrderItems = orderItemsRepository.saveAll(orderItems);

        //SHIPPING ADDRESS
        ShippingAddress shippingAddress = OrderAddressMapper.ToEntity(response.get("metadata.shippingAddress"));
        shippingAddress.setOrder(savedOrder);
        ShippingAddress savedShippingAddress = orderAddressRepository.save(shippingAddress);

        //BILLING ADDRESS
        BillingAddress billingAddress = BillingAddressMapper.toEntity(response.get("metadata.billingAddress"));
        billingAddress.setOrder(savedOrder);
        BillingAddress savedBillingAddress = billingAdressRepository.save(billingAddress);

        //PAYMENT
        Payment payment = Payment.builder()
                .id(response.get("id"))
                .order(response.get("id"))
                .payerDetails()
                .paymentCards()
                .paymentMethodId(response.get("payment_method_id"))
                .paymentTypeId(response.get("payment_type_id"))
                .binaryMode(response.get("binary_mode"))
                .status(response.get("status"))
                .statusDetail(response.get("status_detail"))
                .taxesAmount(response.get("taxes_amount"))
                .shippingAmount(response.get("shipping_amount"))
                .netReceivedAmount(response.get("transaction_details.net_received_amount"))
                .transactionAmount(response.get("transaction_amount"))
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        //PAYER DETAILS
        PayerDetails payerDetails = PayerDetails.builder()
                .payerId()
                .email()
                .identificationType()
                .identificationNumber()
                .type()
                .build();

        PayerDetails savedPayerDetails;

        //PAYMENT CARD
        PaymentCard paymentCard = PaymentCard.builder()
                .paymentCardId()
                .bin()
                .cardholderIdentificationType()
                .cardholderIdentificationNumber()
                .country()
                .expirationMonth()
                .expirationYear()
                .firstSixDigits()
                .lastFourDigits()
                .pay
                .build();

        PayerDetails savedPaymentCard;



        updateProductStock(orderItems);

        return savedOrder==null ? false : true;*/
        return true;
    }

}
