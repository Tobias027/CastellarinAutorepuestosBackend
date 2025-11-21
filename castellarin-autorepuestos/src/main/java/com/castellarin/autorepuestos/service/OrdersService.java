package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.dto.OrderDto;
import com.castellarin.autorepuestos.domain.dto.OrderItemDto;
import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.domain.mappers.BillingAddressMapper;
import com.castellarin.autorepuestos.domain.mappers.OrderAddressMapper;
import com.castellarin.autorepuestos.domain.mappers.OrderMapper;
import com.castellarin.autorepuestos.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final ProductRepository productRepository;
    private OrderRepository orderRepository;
    private final ProductsService productsService;
    private final UserService userService;

    private final OrderAddressRepository orderAddressRepository;
    private final BillingAdressRepository billingAdressRepository;
    private final OrderItemsRepository orderItemsRepository;


    @Transactional
    public Order createOrder(OrderDto orderDto){
        //USUARIO
        User user = userService.getUserByEmail(orderDto.getEmail())
                .orElseThrow(()->new EntityNotFoundException("User not found"));

        //ORDER ITEMS
        List<OrderItem> orderItems = getOrderItems(orderDto.getOrderItemsDto());

        //TOTALES
        Double subtotal = calculateSubtotal(orderItems);
        //TODO
        Double shippingCost = 0.0;
        //TODO
        Double taxAmount = 0.0;

        Double total = subtotal+shippingCost+taxAmount;

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setSubtotal(subtotal);
        order.setTax(taxAmount);
        order.setTotal(total);
        order.setNotes(orderDto.getNotes());
        order.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Buenos_Aires")));

        Order savedOrder = orderRepository.save(order);
        //ORDER ADDRESS
        OrderAddress orderAddress = OrderAddressMapper.ToEntity(orderDto.getOrderAddressDto());
        orderAddress.setOrder(savedOrder);
        orderAddressRepository.save(orderAddress);
        //BILLING ADDRESS
        BillingAddress billingAddress = BillingAddressMapper.toEntity(orderDto.getBillingAddressDto());
        billingAddress.setOrder(savedOrder);
        billingAdressRepository.save(billingAddress);

        for(OrderItem orderItem : orderItems){
            orderItem.setOrder(savedOrder);
        }
        orderItemsRepository.saveAll(orderItems);

        updateProductStock(orderItems);

        return savedOrder;
    }

    private List<OrderItem> getOrderItems(List<OrderItemDto> orderItemDtoList){
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

    private void updateProductStock(List<OrderItem> OrderItems){
        for(OrderItem orderItem : OrderItems){
            Product product = orderItem.getProduct();
            product.setStock(product.getStock() - orderItem.getQuantity());
            productRepository.save(product);
        }
    }

}
