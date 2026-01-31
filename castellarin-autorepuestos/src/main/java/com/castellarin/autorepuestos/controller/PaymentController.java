package com.castellarin.autorepuestos.controller;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.PreferenceDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.security.SignatureVerifier;
import com.castellarin.autorepuestos.service.MercadoPagoService;
import com.castellarin.autorepuestos.service.OrdersService;
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
        Order order = ordersService.createPendingOrder(user,orderDto);
        PreferenceDto preferenceDto = mercadoPagoService.createPreference(order);
        return ResponseEntity.ok(preferenceDto);
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveNotification(@RequestHeader("x-signature") String signature, @RequestHeader("x-request-id") String requestId, @RequestBody Map<String,Object> payload){
        String[] parts = signature.split(",");
        String ts = parts[0].split("=")[1];
        String v1 = parts[1].split("=")[1];

        System.out.println(ts);
        System.out.println(v1);
        System.out.println(requestId);
        System.out.println(payload);
        //String resourceId = data.get("id").toString();
        /*
        if(SignatureVerifier.isValidSignature(resourceId,requestId,ts,v1,webhookSecret)){
            System.out.println("PAYLOAD\n "+payload);
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }*/
        return ResponseEntity.ok("");
    }
}
