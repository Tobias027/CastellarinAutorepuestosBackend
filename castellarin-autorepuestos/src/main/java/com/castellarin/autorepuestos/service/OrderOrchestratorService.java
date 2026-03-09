package com.castellarin.autorepuestos.service;

import com.castellarin.autorepuestos.domain.entity.*;
import com.castellarin.autorepuestos.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderOrchestratorService {

    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final ProductsService productsService;

    @Transactional
    public Order createOrder(Map<String, Object> response) {

        //ORDER
        //TODO
        // .tax()
        // .shipping()
        Order order = Order.builder()
                .orderId("id")
                .status((String) response.get("status"))
                //TODO CAMBIAR NOMBRE DEL ATRIBUTO DENTRO DE LA ENTIDAD Y DEMAS
                .statusDetails((String) response.get("order_status"))
                .total(Double.parseDouble((String) response.get("total_amount")))
                .build();

        final Order savedOrder = orderRepository.save(order);

        //ORDER ITEMS
        List<Map<String, Object>> itemsBody = (List<Map<String, Object>>) response.get("items");
        List<OrderItem> orderItems = itemsBody.stream()
                .map(item ->
                        {
                            Integer quantity = Integer.parseInt(item.get("quantity").toString());
                            Double unitPrice = Double.parseDouble(item.get("unit_price").toString());

                            return OrderItem.builder()
                                    .order(savedOrder)
                                    .product(productsService.getProductByProductPart((String) item.get("external_code")))
                                    .quantity(quantity)
                                    .unitPrice(unitPrice)
                                    .subtotal(unitPrice * quantity)
                                    .build();
                        }
                )
                .collect(Collectors.toList());

        orderItemsRepository.saveAll(orderItems);

        return savedOrder;
    }

    @Transactional
    public Order updateOrder(Map<String, Object> response) {
        Order order = orderRepository.findByOrderId(((String) response.get("id")));

        String newStatus = (String) response.get("status");
        String newStatusDetail = (String) response.get("status_detail");

        if(!order.getStatus().equals(newStatus)){
            order.setStatus(newStatus);
            order.setStatusDetails(newStatusDetail);

            //TODO LOGICA DE STOCK SI PASA A CANCELADO
        }

        return orderRepository.save(order);
    }

    public Order getOrderById(String id){
        return orderRepository.findByOrderId(id);
    }
}
