package dev.jtavarez.order_service.services;

import dev.jtavarez.order_service.model.dtos.BaseResponse;
import dev.jtavarez.order_service.model.dtos.OrderItemsRequest;
import dev.jtavarez.order_service.model.dtos.OrderItemsResponse;
import dev.jtavarez.order_service.model.dtos.OrderRequest;
import dev.jtavarez.order_service.model.dtos.OrderResponse;
import dev.jtavarez.order_service.model.entities.Order;
import dev.jtavarez.order_service.model.entities.OrderItems;
import dev.jtavarez.order_service.repositories.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  public void placeOrder(OrderRequest orderRequest) {
    // Check for inventory
    BaseResponse result = this.webClientBuilder.build()
        .post()
        .uri("lb://inventory-service/api/inventory/in-stock")
        .bodyValue(orderRequest.getOrderItems())
        .retrieve()
        .bodyToMono(BaseResponse.class)
        .block();
    if (result != null && !result.hasErrors()) {


    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());
    order.setOrderItems(orderRequest.getOrderItems().stream()
        .map(orderItemsRequest -> mapOrderItemRequestToOrderItem(orderItemsRequest, order))
        .toList());
    this.orderRepository.save(order);
    } else {
      throw new IllegalArgumentException("Order could not be placed, Product is not in stock");
    }
  }
  public List<OrderResponse> getAllOrders() {
    List<Order> orders = this.orderRepository.findAll();


    return orders.stream().map(this::mapToOrderResponse).toList();
  }

  private OrderResponse mapToOrderResponse(Order order) {
    return new OrderResponse(order.getId(), order.getOrderNumber()
    , order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
  }

  private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems) {
    return new OrderItemsResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
  }


  private OrderItems mapOrderItemRequestToOrderItem(OrderItemsRequest orderRequest, Order order) {
    return OrderItems.builder()
        .id(orderRequest.getId())
        .sku(orderRequest.getSku())
        .price(orderRequest.getPrice())
        .quantity(orderRequest.getQuantity())
        .order(order)
        .build();
  }


}
