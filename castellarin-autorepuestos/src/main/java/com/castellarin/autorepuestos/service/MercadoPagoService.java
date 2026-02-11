package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.PreferenceDto;
import com.castellarin.autorepuestos.domain.entity.OrderItem;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MercadoPagoService {

    @Value("${mp.test.access-token}")
    private String testAccessToken;

    public PreferenceDto createPreference(com.castellarin.autorepuestos.domain.entity.Preference preference){

        MercadoPagoConfig.setAccessToken(testAccessToken);

        List<PreferenceItemRequest> items = new ArrayList<>();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success((String) preference.getBackUrls())
                .build();

        for(OrderItem orderItem : preference.getOrderItems()){
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(orderItem.getProduct().getPartNumber())
                            .title(orderItem.getProduct().getName())
                            .quantity(orderItem.getQuantity())
                            .unitPrice(new BigDecimal(orderItem.getProduct().getPrice()))
                            .currencyId("ARS")
                            .build();
            items.add(itemRequest);
        }
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .shipments(null)
                .binaryMode(true)
                .metadata(preference.getMetadata())
                .backUrls(backUrls)
                .autoReturn("approved")
                .notificationUrl(preference.getNotificationUrl())
                .build();

        PreferenceClient client = new PreferenceClient();

        try {
            Preference mpPreference = client.create(preferenceRequest);
            return  new PreferenceDto(mpPreference.getId());
        } catch (MPException mpException) {
            throw new RuntimeException(mpException);
        } catch (MPApiException apiException) {
            throw new RuntimeException(apiException);
        }
    }

}
