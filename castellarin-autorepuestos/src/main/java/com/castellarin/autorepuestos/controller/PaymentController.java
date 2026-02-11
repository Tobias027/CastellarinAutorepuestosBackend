package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.PreferenceDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.service.MercadoPagoService;
import com.castellarin.autorepuestos.service.OrderOrchestratorService;
import com.castellarin.autorepuestos.service.PreferenceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final OrderOrchestratorService orderOrchestratorService;
    private final PreferenceService preferenceService;
    private final MercadoPagoService mercadoPagoService;
    @Value("${mp.test.webhook-secret-key}")
    private String webhookSecret;

    @Value("${mp.test.access-token}")
    private String accessToken;

    @PostMapping("/create-payment")
    public ResponseEntity<PreferenceDto> createOrder(@RequestBody OrderDto orderDto){
        Preference preference = preferenceService.createPrefence(orderDto);
        PreferenceDto preferenceDto = mercadoPagoService.createPreference(preference);
        return ResponseEntity.ok(preferenceDto);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveNotification(
            @RequestParam("data.id") String dataId,
            @RequestHeader("x-signature") String signature,
            @RequestHeader("x-request-id") String requestId,
            @RequestBody JsonNode payload) {

        System.out.println(dataId);
        System.out.println(signature);
        System.out.println(requestId);

        String type = payload.path("type").asText();
        System.out.println(payload);
        switch (type){
            //EN ESTE CASO SE CREA DE 0
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

                        ObjectMapper mapper = new ObjectMapper();
                        Map<String,Object> responseBody = mapper.readValue(response.body(), Map.class);


                        Boolean order = orderOrchestratorService.createOrder(responseBody);
                        // TODO VER SI ANTES DE CREAR LA PREFERENCIA CHEQUEA STOCK
                        // Order order = ordersService
                        // Shipping Address
                        // Billing Address
                        //Payment
                        //Payer Details
                        //IF CARD PaymentCard

                        //TODO BAJAR STOCK
                        //TODO ENVIAR UN EMAIL
                        // ENVIAR MAIL A NOSOTROS POR NUEVA VENTA??
                        //TODO LOGICA DE CORREO
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
