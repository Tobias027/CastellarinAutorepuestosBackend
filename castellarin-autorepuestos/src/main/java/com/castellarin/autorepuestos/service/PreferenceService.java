package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.OrderItemDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.domain.mappers.BillingAddressMapper;
import com.castellarin.autorepuestos.domain.mappers.OrderAddressMapper;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final UserService userService;
    private final ProductsService productsService;

    @Value("${back_url}")
    private String backUrl;

    @Value("${url-webhook}")
    private String notificationUrl;

    public Preference createPrefence(OrderDto orderDto){

        /*Optional<User> user = userService.getUserByEmail(authenticatedUser.getUsername());*/
        List<OrderItem> orderItems = getOrderItems(orderDto.getOrderItems());

        Double subtotal = calculateSubtotal(orderItems);
        //TODO
        Double shippingCost = 0.0;
        Long total = subtotal.longValue()+shippingCost.longValue();

        String notes = orderDto.getNotes();

        //ORDER ADDRESS
        ShippingAddress shippingAddress = OrderAddressMapper.ToEntity(orderDto.getOrderAddress());
        //BILLING ADDRESS
        BillingAddress billingAddress = BillingAddressMapper.toEntity(orderDto.getBillingAddress());

        Map<String, Object> metadata = new HashMap<>();

        metadata.put("billingAddress", billingAddress);
        metadata.put("notes", notes);
        metadata.put("shipping_address", shippingAddress);

        Preference preference = new Preference();

        //preference.setUser(user);
        preference.setOrderItems(orderItems);
        //TODO ESTO VA DETRO DE SHIPMENTS
        preference.setShippingCost(shippingCost);
        preference.setTotal(total);
        preference.setShipments(shippingAddress);
        preference.setMetadata(metadata);
        preference.setBackUrls(backUrl);
        preference.setNotificationUrl(notificationUrl);

        return preference;
    }

    public List<OrderItem> getOrderItems(List<OrderItemDto> orderItemDtoList){
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto orderItemDto : orderItemDtoList) {
            Product product = productsService.getProductByProductPart(orderItemDto.getPartNumber());
            if (product == null){
                throw new EntityNotFoundException("Product not found: " + orderItemDto.getPartNumber());
            }
            if (product.getStock() < orderItemDto.getQuantity()){
                throw new EntityNotFoundException("Product stock out of stock: " + orderItemDto.getQuantity());
            }
            if (!product.getIsActive()){
                throw new EntityNotFoundException("Product not active in stock: " + orderItemDto.getPartNumber());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDto.getQuantity());
            Double price = product.getOfferPrice() != null && product.getOfferPrice() > 0
                    ? product.getOfferPrice()
                    : product.getPrice();
            orderItem.setSubtotal(price * orderItemDto.getQuantity());
            orderItems.add(orderItem);
        }

        return orderItems;
    }

    private Double calculateSubtotal(List<OrderItem> orderItemList){
        return orderItemList.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

}
