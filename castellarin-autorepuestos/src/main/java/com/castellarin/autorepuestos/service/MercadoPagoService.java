package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.PreferenceDto;
import com.castellarin.autorepuestos.domain.entity.Order;
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

    @Value("${url-webhook}")
    private String urlWebhook;

    @Value("${url}")
    private String url;

    public PreferenceDto createPreference(Order order){

        MercadoPagoConfig.setAccessToken(testAccessToken);

        List<PreferenceItemRequest> items = new ArrayList<>();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(url+"/checkout/success")
                .build();

        for(OrderItem orderItem : order.getItems()){
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(orderItem.getProduct().getPartNumber())
                            .title(orderItem.getProduct().getName())
                            .quantity(orderItem.getQuantity())
                            .currencyId("ARS")
                            .unitPrice(new BigDecimal(orderItem.getProduct().getPrice()))
                            .build();
            items.add(itemRequest);
        }
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .autoReturn("approved")
                .backUrls(backUrls)
                .notificationUrl(urlWebhook)
                .build();

        PreferenceClient client = new PreferenceClient();

        try {
            Preference preference = client.create(preferenceRequest);
            System.out.println(preference.toString());
            return  new PreferenceDto(preference.getId());
        } catch (MPException mpException) {
            throw new RuntimeException(mpException);
        } catch (MPApiException apiException) {
            throw new RuntimeException(apiException);
        }
    }

}
