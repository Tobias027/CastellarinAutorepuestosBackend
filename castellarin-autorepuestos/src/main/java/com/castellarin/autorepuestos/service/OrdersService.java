package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.OrderItemDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.domain.mappers.OrderAddressMapper;
import com.castellarin.autorepuestos.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private OrderRepository orderRepository;
    private final ProductsService productsService;
    private final UserService userService;

    @Transactional
    public Order createOrder(OrderDto orderDto){
        //NUEVA ORDEN
        Order newOrder = new Order();

        //OBTENER ENTIDADES NECESARIAS
        Optional<User> user = Optional.of(userService.getUserByEmail(orderDto.getEmail())
                .orElseThrow(()->new EntityNotFoundException("User not found")));

        OrderAddress orderAddress = OrderAddressMapper.ToEntity(orderDto.getOrderAddressDto());

        List<OrderItem> orderItems = getOrderItems(orderDto.getOrderItemDtoList());

        Double subtotal = getOrderItemsSubtotal(orderItems);

        newOrder.setUser(user.get());
        newOrder.setAddress(orderAddress);
        for(OrderItem orderItem : orderItems){
            newOrder.addOrderItem(orderItem);
        }
        //TODO ver como implementarlo con MP
        newOrder.setStatus(OrderStatus.PENDING);
        newOrder.setSubtotal(subtotal);
        //TODO Ver el tema de los impuestos
        newOrder.setTax(0.0);
        //TODO Ver el tema de los envio
        newOrder.setShipping(0.0);
        newOrder.setTotal(newOrder.getSubtotal() + newOrder.getTax() + newOrder.getShipping());
        newOrder.setNotes(orderDto.getNotes());
        newOrder.setCreatedAt(LocalDateTime.now(ZoneId.of("Argentina/Buenos Aires")));
        newOrder.setUpdatedAt(LocalDateTime.now(ZoneId.of("Argentina/Buenos Aires")));

        return orderRepository.save(newOrder);

    }

    private List<OrderItem> getOrderItems(List<OrderItemDto> orderItemDtoList){
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderItemDtoList) {
            Product product = productsService.getProductByProductPart(orderItemDto.getPartNumber());
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setSubtotal(product.getPrice()*orderItemDto.getQuantity());
        }
        return orderItems;
    }

    private Double getOrderItemsSubtotal(List<OrderItem> orderItemList){
        Double subtotal = 0.0;
        for (OrderItem orderItem : orderItemList) {
            subtotal+=orderItem.getSubtotal();
        }
        return subtotal;
    }

}
