package com.castellarin.autorepuestos.controller;


import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.domain.mappers.OrderMapper;
import com.castellarin.autorepuestos.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;

    //TODO TAL VEZ IMPLEMENTAR AUTHENTICATED PARA OBTENER EL USERID
    @PostMapping("/create_order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        Order order = ordersService.createOrder(orderDto);
        OrderDto orderDtoResponse =  OrderMapper.toDto(order);
        return ResponseEntity.ok(orderDtoResponse);
    }
}
