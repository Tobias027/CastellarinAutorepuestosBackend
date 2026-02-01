package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.PreferenceDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.security.SignatureVerifier;
import com.castellarin.autorepuestos.service.MercadoPagoService;
import com.castellarin.autorepuestos.service.OrdersService;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final OrdersService ordersService;
    private final MercadoPagoService mercadoPagoService;
    @Value("${mp.test.webhook-secret-key}")
    private String webhookSecret;

    @PostMapping("/create-payment")
    public ResponseEntity<PreferenceDto> createOrder(@AuthenticationPrincipal User user, @RequestBody OrderDto orderDto){
        System.out.println(webhookSecret);
        Order order = ordersService.createPendingOrder(user,orderDto);
        PreferenceDto preferenceDto = mercadoPagoService.createPreference(order);
        return ResponseEntity.ok(preferenceDto);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveNotification(@RequestHeader("x-signature") String signature, @RequestHeader("x-request-id") String requestId, @RequestBody Map<String,Object> payload) {
        System.out.println("LLEGO");
        String[] parts = signature.split(",");
        String ts = parts[0].split("=")[1];
        String v1 = parts[1].split("=")[1];

        String resourceId = extractResourceId(payload);

        if (!payload.get("type").equals("payment")) {
            return ResponseEntity.ok("");
        }

        System.out.println("resourceId "+resourceId);
        System.out.println("signature "+signature);
        System.out.println("requestId "+requestId);
        System.out.println("payload "+payload);

        if(SignatureVerifier.isValidSignature(resourceId,requestId,ts,v1,webhookSecret)){
            try{
                System.out.println("PASO LA VALIDACION");
                MerchantOrderClient merchantOrderClient = new MerchantOrderClient();
                System.out.println("\n\n\n\n");
                System.out.println("Merchant order");
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getDateCreated());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getLastUpdated());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getId());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getOrderStatus());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getPayer());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getItems());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).getTotalAmount());
                System.out.println(merchantOrderClient.get(Long.parseLong(resourceId)).isCancelled());
                System.out.println("\n\n\n\n");
            } catch (MPException | MPApiException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    public String extractResourceId(Map<String, Object> payload) {
        if (payload.get("data") instanceof Map) {
            Map<?, ?> data = (Map<?, ?>) payload.get("data");
            if (data.get("id") != null) return data.get("id").toString();
        }

        if (payload.get("resource") != null) {
            String resource = payload.get("resource").toString();
            if (resource.contains("/")) {
                return resource.substring(resource.lastIndexOf("/") + 1);
            }
            return resource;
        }

        if (payload.get("id") != null) {
            return payload.get("id").toString();
        }

        throw new RuntimeException("No resource id");
    }
}
