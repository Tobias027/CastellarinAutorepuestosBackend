package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.entity.Order;
import com.castellarin.autorepuestos.domain.entity.OrderItem;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.resources.preference.PreferenceBackUrls;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    public String createPreference(Order order){
        List<PreferenceItemRequest> items = new ArrayList<>();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("http://localhost:8080/success")
                .pending("http://localhost:8080/pending")
                .failure("http://localhost:8080/failure")
                .build();

        for(OrderItem orderItem : order.getItems()){
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id("")
                            .title(orderItem.getProduct().getName())
                            .description(orderItem.getProduct().getDescription())
                            .pictureUrl(orderItem.getProduct().getImagePath())
                            .categoryId(orderItem.getProduct().getCategory().getCategory())
                            .quantity(orderItem.getQuantity())
                            .currencyId("ARS")
                            .unitPrice(new BigDecimal(orderItem.getProduct().getPrice()))
                            .build();
            items.add(itemRequest);
        }
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .build();
        PreferenceClient client = new PreferenceClient();

        try {
            Preference preference = client.create(preferenceRequest);
            return  preference.getId();

        } catch (MPException mpException) {
            throw new RuntimeException(mpException);
        } catch (MPApiException apiException) {
            throw new RuntimeException(apiException);
        }
    }

}
