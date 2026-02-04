package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.PreferenceDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.security.SignatureVerifier;
import com.castellarin.autorepuestos.service.MercadoPagoService;
import com.castellarin.autorepuestos.service.OrdersService;
import com.fasterxml.jackson.databind.JsonNode;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final OrdersService ordersService;
    private final MercadoPagoService mercadoPagoService;
    @Value("${mp.test.webhook-secret-key}")
    private String webhookSecret;

    @Value("${mp.test.access-token}")
    private String accessToken;

    @PostMapping("/create-payment")
    public ResponseEntity<PreferenceDto> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderDto orderDto){
        Order order = ordersService.createPendingOrder(user,orderDto);
        PreferenceDto preferenceDto = mercadoPagoService.createPreference(order);
        return ResponseEntity.ok(preferenceDto);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveNotification(
            @RequestParam("data.id") String dataId,
            @RequestHeader("x-signature") String signature,
            @RequestHeader("x-request-id") String requestId,
            @RequestBody JsonNode payload) {


        String type = payload.path("type").asText();
        System.out.println(payload);
        switch (type){
            case "payment":
                /*String[] parts = signature.split(",");
                String ts = parts[0].split("=")[1];
                String v1 = parts[1].split("=")[1];
                if(SignatureVerifier.isValidSignature(dataId,requestId,ts,v1,webhookSecret)){*/
                    try{
                        String url = "https://api.mercadopago.com/v1/payments/" + dataId;
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .header("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + accessToken)
                                .GET()
                                .build();

                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        System.out.println(response.statusCode());
                        System.out.println(response.body());
                        System.out.println(response.headers());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                /*}*/

                return ResponseEntity.ok("");
            default:
                return ResponseEntity.ok("");
        }
    }
}
